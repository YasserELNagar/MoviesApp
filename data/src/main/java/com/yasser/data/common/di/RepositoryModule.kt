package com.yasser.data.common.di

import com.yasser.data.genres.repository.GenresRepository
import com.yasser.data.genres.repository.IGenreRepository
import com.yasser.data.movies.repository.IMovieRepository
import com.yasser.data.movies.repository.MovieRepository
import com.yasser.data_local.genres.datasource.IGenresLocalDatasource
import com.yasser.data_local.movies.datasource.IMovieLocalDatasource
import com.yasser.data_remote.genres.datasource.IGenresRemoteDataSource
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
    fun provideGenreRepository(remoteDataSource: IGenresRemoteDataSource,localDatasource: IGenresLocalDatasource): IGenreRepository {
        return GenresRepository(remoteDataSource, localDatasource)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(genresRepository: IGenreRepository,remoteDataSource: IMovieRemoteDataSource,localDatasource: IMovieLocalDatasource): IMovieRepository {
        return MovieRepository(genresRepository,remoteDataSource,localDatasource)
    }



}