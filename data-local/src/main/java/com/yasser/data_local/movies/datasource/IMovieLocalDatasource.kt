package com.yasser.data_local.movies.datasource

import com.yasser.data_local.movies.entities.MovieEntity
import kotlinx.coroutines.flow.Flow


interface IMovieLocalDatasource {
    suspend fun addToFavorites(item: MovieEntity)
    suspend fun removeFromFavorites(item: MovieEntity)
    suspend fun getFavoriteMovies(): Flow<List<MovieEntity>?>
    suspend fun getFavoriteMovie(itemId: Int): MovieEntity?
}