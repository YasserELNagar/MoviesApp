package com.yasser.data_remote.genres.datasource

import com.yasser.data_remote.genres.dto.GenresListDTO

interface IGenresRemoteDataSource {
    suspend fun getMoviesGenresList(): GenresListDTO?
    suspend fun getSeriesGenresList(): GenresListDTO?
}