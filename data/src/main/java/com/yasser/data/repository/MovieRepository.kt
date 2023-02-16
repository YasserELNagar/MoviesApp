package com.yasser.data.repository

import com.yasser.data.mapper.LocalMapper
import com.yasser.data.mapper.RemoteMapper
import com.yasser.data.model.movies.MovieModel
import com.yasser.data_local.movies.datasource.IMovieLocalDatasource
import com.yasser.data_remote.movies.datasource.IMovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: IMovieRemoteDataSource,
    private val localDataSource: IMovieLocalDatasource
) : IMovieRepository {
    override suspend fun getPopularMovies(page: Int): Pair<List<MovieModel?>?, Boolean> {
        val remoteResponse = remoteDataSource.getPopularMovies(page)
        return Pair(
            remoteResponse?.results?.map {
                RemoteMapper.mapToTarget(it)
            },
            page != remoteResponse?.total_pages
        )
    }

    override suspend fun getTopRatedMovies(page: Int): Pair<List<MovieModel?>?, Boolean> {
        val remoteResponse = remoteDataSource.getTopRatedMovies(page)
        return Pair(
            remoteResponse?.results?.map {
                RemoteMapper.mapToTarget(it)
            },
            page != remoteResponse?.total_pages
        )
    }

    override suspend fun getNowPlayingMovies(page: Int): Pair<List<MovieModel?>?, Boolean> {
        val remoteResponse = remoteDataSource.getNowPlayingMovies(page)
        return Pair(
            remoteResponse?.results?.map {
                RemoteMapper.mapToTarget(it)
            },
            page != remoteResponse?.total_pages
        )
    }

    override suspend fun addToFavorites(item: MovieModel) {
        LocalMapper.mapFromTarget(item)?.let {
            localDataSource.addToFavorites(it)
        }
    }

    override suspend fun removeFromFavorites(item: MovieModel) {
        LocalMapper.mapFromTarget(item)?.let {
            localDataSource.removeFromFavorites(it)
        }
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieModel?>?> {
        return localDataSource.getFavoriteMovies().map { items ->
            items?.map {
                LocalMapper.mapToTarget(it)
            }
        }
    }

    override suspend fun getFavoriteMovie(itemId: Int): MovieModel? {
        return LocalMapper.mapToTarget(localDataSource.getFavoriteMovie(itemId))
    }
}