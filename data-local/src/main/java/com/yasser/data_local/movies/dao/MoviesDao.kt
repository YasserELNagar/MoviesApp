package com.yasser.data_local.movies.dao

import androidx.room.*
import com.yasser.data_local.movies.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(item: MovieEntity)

    @Delete
    suspend fun removeFromFavorites(item: MovieEntity)

    @Query("Select * From FavoriteMovies Order BY addedToFavoriteDate Desc")
    fun getFavoriteMovies(): Flow<List<MovieEntity>?>

    @Query("Select * From FavoriteMovies Where id = :itemId")
    suspend fun getFavoriteMovie(itemId: Int): MovieEntity?
}