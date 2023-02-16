package com.yasser.data_local.genres.di

import com.yasser.data_local.movies.datasource.MovieLocalDatasource
import com.yasser.data_local.common.ICoroutineDispatchers
import com.yasser.data_local.genres.dao.GenresDao
import com.yasser.data_local.genres.datasource.GenresLocalDatasource
import com.yasser.data_local.genres.datasource.IGenresLocalDatasource
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
    fun provideGenresLocalDataSource(genresDao: GenresDao, coroutineDispatchers: ICoroutineDispatchers): IGenresLocalDatasource {
        return GenresLocalDatasource(genresDao,coroutineDispatchers)
    }

}