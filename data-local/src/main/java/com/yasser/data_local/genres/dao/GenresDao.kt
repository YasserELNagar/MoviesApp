package com.yasser.data_local.genres.dao

import androidx.room.*
import com.yasser.data_local.genres.entities.GenreEntity
import com.yasser.data_local.genres.entities.GenreType
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenresList(item: List<GenreEntity>)
    @Query("Select * From Genres where type = :genreType Order BY name")
    fun getGenresList(genreType: String): Flow<List<GenreEntity>?>
}