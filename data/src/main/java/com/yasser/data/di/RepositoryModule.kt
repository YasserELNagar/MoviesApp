package com.yasser.data.di

import com.yasser.data.repository.IMovieRepository
import com.yasser.data.repository.MovieRepository
import com.yasser.data_local.movies.dao.MoviesDao
import com.yasser.data_local.movies.datasource.IMovieLocalDatasource
import com.yasser.data_local.movies.datasource.MovieLocalDatasource
import com.yasser.data_local.common.ICoroutineDispatchers
import com.yasser.data_remote.movies.datasource.IMovieRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(remoteDataSource: IMovieRemoteDataSource,localDatasource: IMovieLocalDatasource): IMovieRepository {
        return MovieRepository(remoteDataSource,localDatasource)
    }

}