package com.yasser.data.model.movies

import androidx.annotation.Keep

@Keep
data class MoviesLisModel(
    val page: Int?,
    val results: List<MovieModel>?,
    val total_pages: Int?,
    val total_results: Int?
)