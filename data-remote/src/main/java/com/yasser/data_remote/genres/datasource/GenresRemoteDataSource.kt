package com.yasser.data_remote.genres.datasource

import com.yasser.data_remote.common.ICoroutineDispatchers
import com.yasser.data_remote.common.NetworkUtil
import com.yasser.data_remote.genres.dto.GenresListDTO
import com.yasser.data_remote.genres.service.GenresServicesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenresRemoteDataSource @Inject constructor (
    private val api: GenresServicesApi,
    private val coroutineScopeDispatchers: ICoroutineDispatchers
    ): IGenresRemoteDataSource {

    override suspend fun getMoviesGenresList(): GenresListDTO? {
        return withContext(coroutineScopeDispatchers.IO){
            NetworkUtil.processAPICall { api.moviesGenresList() }
        }
    }

    override suspend fun getSeriesGenresList(): GenresListDTO? {
        return withContext(coroutineScopeDispatchers.IO){
            NetworkUtil.processAPICall { api.seriesGenresList() }
        }
    }

}