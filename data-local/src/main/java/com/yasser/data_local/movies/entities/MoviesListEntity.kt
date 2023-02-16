package com.yasser.data_local.movies.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
data class MoviesListEntity(
    val page: Int?,
    val results: List<MovieEntity>?,
    val total_pages: Int?,
    val total_results: Int?
)