package com.yasser.data_remote.common.apiResponse

import com.google.gson.annotations.SerializedName

enum class APIStatusCode(val value:Int) {
    @SerializedName("200")
    SUCCESS(200),
    @SerializedName("404")
    NOT_FOUND(404),
    @SerializedName("401")
    UN_AUTHORIZED(401)
}