package com.yasser.data_remote.common


import com.yasser.data_remote.common.Const.NETWORK_BASE_URL
import com.yasser.data_remote.common.Const.NETWORK_CONNECTION_TIME_OUT
import com.yasser.data_remote.common.Const.NETWORK_READ_TIME_OUT
import com.yasser.data_remote.common.interceptor.AppInterceptor
import com.yasser.data_remote.movies.service.MoviesServicesApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yasser.data_remote.genres.service.GenresServicesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideHttpOkClient(interceptor: AppInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(NETWORK_READ_TIME_OUT,TimeUnit.SECONDS)
            .connectTimeout(NETWORK_CONNECTION_TIME_OUT,TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NETWORK_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesServicesApi(retrofit: Retrofit): MoviesServicesApi {
        return retrofit.create(MoviesServicesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGenreServicesApi(retrofit: Retrofit): GenresServicesApi {
        return retrofit.create(GenresServicesApi::class.java)
    }

    @Singleton
    @Provides
    fun providesCoroutineDispatcher():ICoroutineDispatchers{
        return AppCoroutineDispatchers()
    }

}