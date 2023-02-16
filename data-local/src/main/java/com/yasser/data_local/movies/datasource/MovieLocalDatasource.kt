package com.yasser.data_local.movies.datasource

import com.yasser.data_local.common.ICoroutineDispatchers
import com.yasser.data_local.movies.dao.MoviesDao
import com.yasser.data_local.movies.entities.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDatasource @Inject constructor(
    private val dao: MoviesDao, private val coroutineScopeDispatchers: ICoroutineDispatchers
) : IMovieLocalDatasource {
    override suspend fun getMovies(): Flow<List<MovieEntity>?> {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.getMovies()
        }
    }

    override suspend fun addMovies(items: List<MovieEntity>) {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.addMovies(items)
        }
    }

    override suspend fun getMovie(itemId: Int): MovieEntity? {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.getMovie(itemId)
        }
    }

    override suspend fun removeMovies() {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.clearMovies()
        }
    }

    override suspend fun addToFavorites(item: MovieEntity) {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.addToFavorites(item)
        }
    }

    override suspend fun removeFromFavorites(itemId: Int) {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.removeFromFavorites(itemId)
        }
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieEntity>?> {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.getFavoriteMovies()
        }
    }
}