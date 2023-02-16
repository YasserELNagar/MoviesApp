package com.yasser.data_local.genres.datasource

import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_local.genres.entities.GenreType
import kotlinx.coroutines.flow.Flow


interface IGenresLocalDatasource {
    suspend fun getGenreList(genreType: GenreType): Flow<List<GenreEntity>?>
    suspend fun addGenreList(genres:List<GenreEntity>)
}