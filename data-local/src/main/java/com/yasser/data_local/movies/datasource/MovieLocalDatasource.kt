package com.yasser.data_local.movies.datasource

import com.yasser.data_local.common.ICoroutineDispatchers
import com.yasser.data_local.movies.dao.MoviesDao
import com.yasser.data_local.movies.entities.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDatasource @Inject constructor(private val dao: MoviesDao, private val coroutineScopeDispatchers: ICoroutineDispatchers
    ) : IMovieLocalDatasource {
    override suspend fun addToFavorites(item: MovieEntity) {
        return withContext(coroutineScopeDispatchers.IO){
            dao.addToFavorites(item)
        }
    }

    override suspend fun removeFromFavorites(item: MovieEntity) {
        return withContext(coroutineScopeDispatchers.IO){
            dao.removeFromFavorites(item)
        }
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieEntity>?> {
        return withContext(coroutineScopeDispatchers.IO){
            dao.getFavoriteMovies()
        }
    }

    override suspend fun getFavoriteMovie(itemId: Int): MovieEntity? {
        return withContext(coroutineScopeDispatchers.IO){
            dao.getFavoriteMovie(itemId)
        }
    }


}