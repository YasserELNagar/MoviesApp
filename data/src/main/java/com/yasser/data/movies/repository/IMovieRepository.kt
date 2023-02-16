package com.yasser.data.movies.repository

import com.yasser.data.movies.model.MovieModel
import kotlinx.coroutines.flow.Flow


interface IMovieRepository {
    suspend fun loadPopularMovies(page:Int): Boolean?
    suspend fun loadTopRatedMovies(page:Int): Boolean?
    suspend fun loadNowPlayingMovies(page:Int): Boolean?
    suspend fun searchForMovie(query:String,page:Int): Pair<List<MovieModel>?,Boolean>?
    suspend fun getMoviesList(): Flow<List<MovieModel>?>
    suspend fun getMovieDetails(itemId: Int): MovieModel?
    suspend fun addToFavorites(item: MovieModel)
    suspend fun removeFromFavorites(item: MovieModel)
    suspend fun getFavoriteMovies(): Flow<List<MovieModel>?>
}