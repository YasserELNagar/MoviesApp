package com.yasser.data_local.genres.datasource

import com.yasser.data_local.common.ICoroutineDispatchers
import com.yasser.data_local.genres.dao.GenresDao
import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_local.genres.entities.GenreType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenresLocalDatasource @Inject constructor(
    private val dao: GenresDao,
    private val coroutineScopeDispatchers: ICoroutineDispatchers
) : IGenresLocalDatasource {

    override suspend fun getGenreList(genreType:GenreType): Flow<List<GenreEntity>?> {
        return withContext(coroutineScopeDispatchers.IO) {
            dao.getGenresList(genreType.name)
        }
    }

    override suspend fun addGenreList(genres: List<GenreEntity>) {
        withContext(coroutineScopeDispatchers.IO) {
            dao.addGenresList(genres)
        }
    }
}