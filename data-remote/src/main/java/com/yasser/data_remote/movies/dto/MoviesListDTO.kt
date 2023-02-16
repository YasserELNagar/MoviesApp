package com.yasser.data_remote.movies.dto

import androidx.annotation.Keep
import com.yasser.data_remote.common.apiResponse.GeneralApiResponse

@Keep
data class MoviesListDTO(
    val page: Int?,
    val results: List<MovieDTO>?,
    val total_pages: Int?,
    val total_results: Int?
): GeneralApiResponse()