package com.yasser.data.repository

import com.yasser.data.model.movies.MovieModel
import kotlinx.coroutines.flow.Flow


interface IMovieRepository {
    suspend fun getPopularMovies(page:Int): Pair<List<MovieModel?>?,Boolean>
    suspend fun getTopRatedMovies(page:Int): Pair<List<MovieModel?>?,Boolean>
    suspend fun getNowPlayingMovies(page:Int): Pair<List<MovieModel?>?,Boolean>
    suspend fun addToFavorites(item: MovieModel)
    suspend fun removeFromFavorites(item: MovieModel)
    suspend fun getFavoriteMovies(): Flow<List<MovieModel?>?>
    suspend fun getFavoriteMovie(itemId: Int): MovieModel?
}