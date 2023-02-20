package com.yasser.data_remote.common.apiResponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
abstract class GeneralApiResponse {
    abstract val status_code: APIStatusCode?
    abstract val status_message: String?
}