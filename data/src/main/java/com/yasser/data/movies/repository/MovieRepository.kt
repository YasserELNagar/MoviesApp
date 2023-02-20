package com.yasser.data.movies.repository

import com.yasser.data.genres.model.GenreModel
import com.yasser.data.genres.repository.IGenreRepository
import com.yasser.data.movies.mapper.MoviesDataMapper
import com.yasser.data.movies.mapper.MoviesLocalMapper
import com.yasser.data.movies.mapper.MoviesRemoteMapper
import com.yasser.data.movies.model.MovieModel
import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_local.movies.datasource.IMovieLocalDatasource
import com.yasser.data_local.movies.entities.MovieEntity
import com.yasser.data_remote.movies.datasource.IMovieRemoteDataSource
import com.yasser.data_remote.movies.dto.MoviesListDTO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val genresRepository: IGenreRepository,
    private val moviesRemoteDataSource: IMovieRemoteDataSource,
    private val moviesLocalDataSource: IMovieLocalDatasource,
) : IMovieRepository {
    override suspend fun loadPopularMoviesWithPagination(page: Int): Boolean? {
        val remoteResponse= moviesRemoteDataSource.getPopularMovies(page)
        return syncMoviesDetailsWithLocalData(remoteResponse, page)
    }

    override suspend fun loadTopRatedMovies(page: Int): Boolean? {
        val remoteResponse= moviesRemoteDataSource.getTopRatedMovies(page)
        return syncMoviesDetailsWithLocalData(remoteResponse, page)
    }

    override suspend fun loadNowPlayingMovies(page: Int): Boolean? {
        val remoteResponse= moviesRemoteDataSource.getNowPlayingMovies(page)
        return syncMoviesDetailsWithLocalData(remoteResponse, page)
    }

    suspend fun syncMoviesDetailsWithLocalData(
        remoteResponse: MoviesListDTO?,
        page: Int
    ): Boolean {

        if (shouldRemoveMovies(page, remoteResponse)) {
            moviesLocalDataSource.removeMovies()
        }

        remoteResponse?.let { remoteMoviesList ->
            // Map remote data to target format and handle details
            val localMoviesList = remoteMoviesList.results?.map {
                MoviesRemoteMapper.mapToTarget(it)
            }?.also {
                handleLoadedAtDate(it)
                handleIsFavoriteMoviesStatus(it,moviesLocalDataSource.getFavoriteMovies().firstOrNull())
                handleMoviesGenres(it,genresRepository.getMoviesGenres().firstOrNull())
            }

            // Save the updated list to the local data source
            localMoviesList?.let {
                saveMoviesList(it)
            }
        }
        // Return true if there are more pages to load, else false
        return page != remoteResponse?.total_pages
    }

    private fun shouldRemoveMovies(
        page: Int,
        remoteResponse: MoviesListDTO?
    ) = page == 1 && !remoteResponse?.results.isNullOrEmpty()

    override suspend fun searchForMovie(query: String, page: Int): Pair<List<MovieModel>?,Boolean>? {
        val remoteResponse = moviesRemoteDataSource.searchForMovie(query,page)
        return Pair(
            remoteResponse?.results?.map {
                MoviesDataMapper.mapToTarget(it)
            },
            page != remoteResponse?.total_pages
        )
    }

    override suspend fun getMoviesList(): Flow<List<MovieModel>?> {
        return moviesLocalDataSource.getMovies().map { localMoviesList ->
            localMoviesList?.map {
                MoviesLocalMapper.mapToTarget(it)
            }
        }
    }

    override suspend fun getMovieDetails(itemId: Int): MovieModel {
        return MoviesLocalMapper.mapToTarget(
            moviesLocalDataSource.getMovie(itemId)
        )
    }

    override suspend fun addToFavorites(item: MovieModel) {
        MoviesLocalMapper.mapFromTarget(item).let {
            moviesLocalDataSource.addToFavorites(it)
        }
    }

    override suspend fun removeFromFavorites(item: MovieModel) {
        item.id?.let {
            moviesLocalDataSource.removeFromFavorites(it)
        }
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieModel>?> {
        return moviesLocalDataSource.getFavoriteMovies().map { localMoviesList ->
            localMoviesList?.map {
                MoviesLocalMapper.mapToTarget(it)
            }
        }
    }

    private suspend fun saveMoviesList(items: List<MovieEntity>) {
        moviesLocalDataSource.addMovies(items)
    }


    suspend fun handleIsFavoriteMoviesStatus(movieEntities: List<MovieEntity>?,favoriteList:List<MovieEntity>?): List<MovieEntity>? {
        val mutableMoviesList = movieEntities?.toMutableList()
        mutableMoviesList?.forEachIndexed{index, movieItem->
            val targetItem = favoriteList?.firstOrNull { it.id == movieItem.id && it.addedToFavoriteDate!=null}
            if (targetItem != null) {
                mutableMoviesList[index].addedToFavoriteDate=targetItem.addedToFavoriteDate
            }
        }
        return mutableMoviesList
    }

    fun handleLoadedAtDate(items: List<MovieEntity>?): List<MovieEntity>? {
        val modifiedItems=items?.mapIndexed{index, movieEntity ->
            movieEntity.loadedAtDate=System.currentTimeMillis()+index
            movieEntity
        }
        return modifiedItems
    }

    suspend fun handleMoviesGenres(items: List<MovieEntity>?,genresList:List<GenreModel>?): List<MovieEntity>? {
        items?.mapIndexed{index, movieItem ->
            val mappedGenresList=movieItem.genres?.map { genre->
                val targetGenre =genresList?.firstOrNull { genre.id == it.id}
                GenreEntity(
                    targetGenre?.id,
                    targetGenre?.name
                )
            }
            movieItem.genres=mappedGenresList
        }
        return items
    }


}