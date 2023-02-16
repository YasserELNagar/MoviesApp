package com.yasser.data_remote.common

import com.yasser.data_remote.common.apiResponse.APIStatusCode
import com.yasser.data_remote.common.apiResponse.GeneralApiResponse
import retrofit2.Response
import java.io.IOException

object NetworkUtil {

    suspend fun <T: GeneralApiResponse>processAPICall(call:suspend ()->Response<T>):T?{
        try {
            val response = call.invoke()

            when(response.body()?.statusCode){
                APIStatusCode.SUCCESS->{
                    return response.body()
                }
                APIStatusCode.UN_AUTHORIZED->{
                    throw AppException.UnAuthorizedException
                }
                else->{
                 throw AppException.GeneralApiError(response.body()?.statusMessage)
                }
            }

        } catch (t: Throwable) {
            if (t is IOException) {
                throw AppException.NetworkException
            } else {
                throw AppException.UnKnownException(t.message)
            }
        }
    }
}