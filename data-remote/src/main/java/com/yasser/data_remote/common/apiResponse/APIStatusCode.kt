package com.yasser.data_remote.common.apiResponse

import com.google.gson.annotations.SerializedName

enum class APIStatusCode {
    @SerializedName("200")
    SUCCESS,
    @SerializedName("404")
    NOT_FOUND,
    @SerializedName("401")
    UN_AUTHORIZED
}