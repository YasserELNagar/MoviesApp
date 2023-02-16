package com.yasser.data_remote.genres.service

import com.yasser.data_remote.genres.dto.GenresListDTO
import com.yasser.data_remote.movies.dto.MoviesListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresServicesApi {

    @GET("genre/movie/list")
    suspend fun moviesGenresList():Response<GenresListDTO>

    @GET("genre/tv/list")
    suspend fun seriesGenresList():Response<GenresListDTO>

}