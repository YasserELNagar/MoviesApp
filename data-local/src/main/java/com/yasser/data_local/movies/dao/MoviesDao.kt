package com.yasser.data_local.movies.dao

import androidx.room.*
import com.yasser.data_local.movies.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(item: MovieEntity)
    @Query("UPDATE Movies SET addedToFavoriteDate=null WHERE id = :itemId")
    suspend fun removeFromFavorites(itemId: Int)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(items: List<MovieEntity>)
    @Query("Select * From Movies Order BY loadedAtDate Asc")
    fun getMovies(): Flow<List<MovieEntity>?>
    @Query("DELETE From Movies Where addedToFavoriteDate is null")
    fun clearMovies()
    @Query("Select * From Movies Where addedToFavoriteDate is not null Order BY addedToFavoriteDate Desc")
    fun getFavoriteMovies(): Flow<List<MovieEntity>?>
    @Query("Select * From Movies Where id = :itemId")
    suspend fun getMovie(itemId: Int): MovieEntity?
}