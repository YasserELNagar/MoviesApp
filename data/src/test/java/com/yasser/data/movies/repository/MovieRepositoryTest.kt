package com.yasser.data.movies.repository

import com.yasser.data.genres.repository.IGenreRepository
import com.yasser.data_local.movies.datasource.IMovieLocalDatasource
import com.yasser.data_local.movies.entities.MovieEntity
import com.yasser.data_remote.movies.datasource.IMovieRemoteDataSource
import com.yasser.data_remote.movies.dto.MovieDTO
import com.yasser.data_remote.movies.dto.MoviesListDTO
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest {

    @MockK
    lateinit var genresRepository: IGenreRepository
    @MockK
    lateinit var moviesRemoteDataSource: IMovieRemoteDataSource
    @MockK
    lateinit var moviesLocalDataSource: IMovieLocalDatasource

    lateinit var movieRepository: MovieRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        movieRepository = MovieRepository(genresRepository,moviesRemoteDataSource,moviesLocalDataSource)
    }

    @Test
    fun `syncMoviesDetailsWithLocalData() with page 1 and successful api response then clear old cached movies`() {
        runTest {

            val moviesRemoteResponse=MoviesListDTO(
                results = listOf(MovieDTO(id = 1), MovieDTO(id = 2))
            )

            coEvery { moviesRemoteDataSource.getPopularMovies(eq(1)) } returns  moviesRemoteResponse
            coEvery { moviesLocalDataSource.getFavoriteMovies() } returns emptyFlow()
            coEvery { genresRepository.getMoviesGenres() } returns emptyFlow()
            coEvery { moviesLocalDataSource.removeMovies() } just Runs
            coEvery { moviesLocalDataSource.addMovies(any()) } just Runs

            movieRepository.syncMoviesDetailsWithLocalData(moviesRemoteResponse,1)

            coVerify {moviesLocalDataSource.removeMovies()}
        }
    }

    @Test
    fun `syncMoviesDetailsWithLocalData() with page over than 1 then will not clear old cached movies`() {
        runTest {


            val moviesRemoteResponse=MoviesListDTO(
                results = listOf(MovieDTO(id = 1), MovieDTO(id = 2))
            )

            coEvery { moviesRemoteDataSource.getPopularMovies(more(1)) } returns  moviesRemoteResponse
            coEvery { moviesLocalDataSource.getFavoriteMovies() } returns emptyFlow()
            coEvery { genresRepository.getMoviesGenres() } returns emptyFlow()
            coEvery { moviesLocalDataSource.removeMovies() } just Runs
            coEvery { moviesLocalDataSource.addMovies(any()) } just Runs

            movieRepository.syncMoviesDetailsWithLocalData(moviesRemoteResponse,2)

            coVerify(exactly = 0) {moviesLocalDataSource.removeMovies()}
        }
    }

    @Test
    fun `handleIsFavoriteMoviesStatus() with favorite list matching movies list then return movies list with correct addedToFavoriteDate`() {
        runTest {

            val moviesList =listOf(MovieEntity(id = 1), MovieEntity(id = 2))
            val favoriteMoviesList =listOf(MovieEntity(id = 1, addedToFavoriteDate = 123), MovieEntity(id = 2, addedToFavoriteDate = 12345))

            val actualMoviesList = movieRepository.handleIsFavoriteMoviesStatus(moviesList, favoriteMoviesList)

            assertEquals(favoriteMoviesList.toString(),actualMoviesList.toString())
        }
    }

    @Test
    fun `handleIsFavoriteMoviesStatus() with favorite list un matching movies list then return movies list with empty addedToFavoriteDate`() {
        runTest {

            val moviesList =listOf(MovieEntity(id = 1), MovieEntity(id = 2))
            val favoriteMoviesList =listOf(MovieEntity(id = 2, addedToFavoriteDate = 123), MovieEntity(id = 4, addedToFavoriteDate = 12345))

            val actualMoviesList = movieRepository.handleIsFavoriteMoviesStatus(moviesList, favoriteMoviesList)

            assertEquals(moviesList.toString(),actualMoviesList.toString())
        }
    }

}