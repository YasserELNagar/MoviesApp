package com.yasser.data_local.movies.datasource

import com.yasser.data_local.movies.entities.MovieEntity
import kotlinx.coroutines.flow.Flow


interface IMovieLocalDatasource {
    suspend fun getMovies(): Flow<List<MovieEntity>?>
    suspend fun addMovies(items:List<MovieEntity>)
    suspend fun getMovie(itemId: Int): MovieEntity?
    suspend fun removeMovies()
    suspend fun addToFavorites(item: MovieEntity)
    suspend fun removeFromFavorites(itemId: Int)
    suspend fun getFavoriteMovies(): Flow<List<MovieEntity>?>
}