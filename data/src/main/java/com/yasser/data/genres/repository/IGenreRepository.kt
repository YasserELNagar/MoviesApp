package com.yasser.data.genres.repository

import com.yasser.data.genres.model.GenreModel
import com.yasser.data.movies.model.MovieModel
import kotlinx.coroutines.flow.Flow


interface IGenreRepository {
    suspend fun getMoviesGenres(): Flow<List<GenreModel>?>
    suspend fun getSeriesGenres(): Flow<List<GenreModel>?>
}