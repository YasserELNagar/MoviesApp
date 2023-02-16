package com.yasser.data_remote.genres.di

import com.yasser.data_remote.common.ICoroutineDispatchers
import com.yasser.data_remote.genres.datasource.GenresRemoteDataSource
import com.yasser.data_remote.genres.datasource.IGenresRemoteDataSource
import com.yasser.data_remote.genres.service.GenresServicesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GenresModule {

    @Singleton
    @Provides
    fun provideGenresRemoteDataSource(servicesApi: GenresServicesApi, coroutineDispatchers: ICoroutineDispatchers): IGenresRemoteDataSource {
        return GenresRemoteDataSource(servicesApi,coroutineDispatchers)
    }

}