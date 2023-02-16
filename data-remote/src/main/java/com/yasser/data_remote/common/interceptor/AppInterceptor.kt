package com.yasser.data_remote.common.interceptor

import com.yasser.data_remote.BuildConfig
import com.yasser.data_remote.common.Const.AUTHORIZATION_QUERY
import com.yasser.data_remote.common.Const.LANG_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class AppInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url.newBuilder()
            .addQueryParameter(LANG_HEADER, getDefaultLanguage())
            .addQueryParameter(AUTHORIZATION_QUERY, BuildConfig.MOVIES_API_KEY)
            .build()

        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }

    private fun getDefaultLanguage():String{
        return Locale.getDefault().language
    }
}
