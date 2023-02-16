package com.yasser.data_remote.movies.di

import com.yasser.data_remote.common.ICoroutineDispatchers
import com.yasser.data_remote.movies.datasource.IMovieRemoteDataSource
import com.yasser.data_remote.movies.datasource.MovieRemoteDataSource
import com.yasser.data_remote.movies.service.MoviesServicesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(servicesApi: MoviesServicesApi, coroutineDispatchers: ICoroutineDispatchers): IMovieRemoteDataSource {
        return MovieRemoteDataSource(servicesApi,coroutineDispatchers)
    }

}