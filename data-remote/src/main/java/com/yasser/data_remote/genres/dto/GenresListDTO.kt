package com.yasser.data_remote.genres.dto

import com.google.gson.annotations.Expose
import com.yasser.data_remote.common.apiResponse.APIStatusCode
import com.yasser.data_remote.common.apiResponse.GeneralApiResponse

data class GenresListDTO(
    val genres: List<GenreDTO>,
    @Expose
    override val status_code: APIStatusCode,
    @Expose
    override val status_message: String?
):GeneralApiResponse()