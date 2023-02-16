package com.yasser.data.movies.repository

import com.yasser.data.genres.repository.IGenreRepository
import com.yasser.data.movies.mapper.MoviesDataMapper
import com.yasser.data.movies.mapper.MoviesLocalMapper
import com.yasser.data.movies.mapper.MoviesRemoteMapper
import com.yasser.data.movies.model.MovieModel
import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_local.movies.datasource.IMovieLocalDatasource
import com.yasser.data_local.movies.entities.MovieEntity
import com.yasser.data_remote.movies.datasource.IMovieRemoteDataSource
import com.yasser.data_remote.movies.dto.MovieDTO
import com.yasser.data_remote.movies.dto.MoviesListDTO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val genresRepository: IGenreRepository,
    private val moviesRemoteDataSource: IMovieRemoteDataSource,
    private val moviesLocalDataSource: IMovieLocalDatasource,
) : IMovieRepository {
    override suspend fun loadPopularMovies(page: Int): Boolean? {
        val remoteResponse= moviesRemoteDataSource.getPopularMovies(page)
        return handleMoviesSyncDetails(remoteResponse, page)
    }

    override suspend fun loadTopRatedMovies(page: Int): Boolean? {
        val remoteResponse= moviesRemoteDataSource.getTopRatedMovies(page)
        return handleMoviesSyncDetails(remoteResponse, page)
    }

    override suspend fun loadNowPlayingMovies(page: Int): Boolean? {
        val remoteResponse= moviesRemoteDataSource.getNowPlayingMovies(page)
        return handleMoviesSyncDetails(remoteResponse, page)
    }

    private suspend fun handleMoviesSyncDetails(
        remoteResponse: MoviesListDTO?,
        page: Int
    ): Boolean {
        if (page==1 && !remoteResponse?.results.isNullOrEmpty()){
            moviesLocalDataSource.removeMovies()
        }

        remoteResponse.let { remoteMoviesList ->
            val localMoviesList = remoteMoviesList?.results?.map {
                MoviesRemoteMapper.mapToTarget(it)
            }.also {
                handleLoadedAtDate(it)
            }.also {
                handleIsFavoriteMoviesStatus(it)
            }.also {
                handleMoviesGenres(it)
            }

            localMoviesList?.let {
                saveMoviesList(it)
            }
        }
        return page != remoteResponse?.total_pages
    }

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


    private suspend fun handleIsFavoriteMoviesStatus(movieEntities: List<MovieEntity>?): List<MovieEntity>? {
        val mutableMoviesList = movieEntities?.toMutableList()
        val favoriteList = moviesLocalDataSource.getFavoriteMovies().firstOrNull()
        mutableMoviesList?.forEachIndexed{index, movieItem->
            val targetItem = favoriteList?.firstOrNull { it.id == movieItem.id && it.addedToFavoriteDate!=null}
            if (targetItem != null) {
                mutableMoviesList[index].addedToFavoriteDate=targetItem.addedToFavoriteDate
            }
        }
        return mutableMoviesList
    }

    private fun handleLoadedAtDate(items: List<MovieEntity>?): List<MovieEntity>? {
        val modifiedItems=items?.mapIndexed{index, movieEntity ->
            movieEntity.loadedAtDate=System.currentTimeMillis()+index
            movieEntity
        }
        return modifiedItems
    }

    private suspend fun handleMoviesGenres(items: List<MovieEntity>?): List<MovieEntity>? {
        val genresList= genresRepository.getMoviesGenres().firstOrNull()
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