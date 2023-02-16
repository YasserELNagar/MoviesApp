package com.yasser.data.genres.repository

import com.yasser.data.genres.mapper.GenresLocalMapperMapper
import com.yasser.data.genres.mapper.GenresRemoteMapperMapper
import com.yasser.data.genres.model.GenreModel
import com.yasser.data_local.genres.datasource.IGenresLocalDatasource
import com.yasser.data_local.genres.entities.GenreType
import com.yasser.data_remote.genres.datasource.IGenresRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val remoteDataSource: IGenresRemoteDataSource,
    private val localDatasource: IGenresLocalDatasource,
) : IGenreRepository {
    override suspend fun getMoviesGenres(): Flow<List<GenreModel>?> {
        val genresList=localDatasource.getGenreList(GenreType.MOVIES).firstOrNull()
        if (genresList.isNullOrEmpty()){
            val moviesGenresList = remoteDataSource.getMoviesGenresList()?.genres?.map {
                GenresRemoteMapperMapper.mapToTarget(it).also {
                    it.type=GenreType.MOVIES
                }
            }
            moviesGenresList?.let {
                localDatasource.addGenreList(it)
            }
        }
        return localDatasource.getGenreList(GenreType.MOVIES).map {localGenresList->
            localGenresList?.map {
                GenresLocalMapperMapper.mapToTarget(it)
            }
        }
    }

    override suspend fun getSeriesGenres(): Flow<List<GenreModel>?> {
        val genresList =localDatasource.getGenreList(GenreType.SERIES).firstOrNull()
        if (genresList.isNullOrEmpty()){
            val seriesGenresList = remoteDataSource.getSeriesGenresList()?.genres?.map {
                GenresRemoteMapperMapper.mapToTarget(it).also {
                    it.type=GenreType.SERIES
                }
            }
            seriesGenresList?.let {
                localDatasource.addGenreList(it)
            }
        }
        return localDatasource.getGenreList(GenreType.SERIES).map {localGenresList->
            localGenresList?.map {
                GenresLocalMapperMapper.mapToTarget(it)
            }
        }
    }


}