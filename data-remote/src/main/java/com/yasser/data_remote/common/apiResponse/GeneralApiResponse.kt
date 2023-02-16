package com.yasser.data_remote.common.apiResponse

import com.google.gson.annotations.SerializedName

open class GeneralApiResponse(
    @SerializedName("status_code")
    val statusCode: APIStatusCode?=null,
    @SerializedName("status_message")
    val statusMessage: String?=null
)