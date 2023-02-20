package com.yasser.data_remote.movies.dto

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.yasser.data_remote.common.apiResponse.APIStatusCode
import com.yasser.data_remote.common.apiResponse.GeneralApiResponse

@Keep
data class MoviesListDTO(
    val page: Int?=null,
    val results: List<MovieDTO>?=null,
    val total_pages: Int?=null,
    val total_results: Int?=null,
    @Expose
    override val status_code: APIStatusCode?=null,
    @Expose
    override val status_message: String?=null
): GeneralApiResponse()