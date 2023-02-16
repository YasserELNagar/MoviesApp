package com.yasser.data_remote.movies.service

import com.yasser.data_remote.movies.dto.MoviesListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesServicesApi {

    @GET("movie/popular")
    suspend fun popularMovies(@Query("page") page:Int):Response<MoviesListDTO>

    @GET("movie/top_rated")
    suspend fun topRatedMovies(@Query("page") page:Int):Response<MoviesListDTO>

    @GET("now_playing")
    suspend fun nowPlayingMovies(@Query("page") page:Int):Response<MoviesListDTO>
}