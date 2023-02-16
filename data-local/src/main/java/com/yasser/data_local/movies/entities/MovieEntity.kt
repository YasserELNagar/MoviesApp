package com.yasser.data_local.movies.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasser.data_local.genres.entities.GenreEntity

@Keep
@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    var genres: List<GenreEntity>?=null,
    var addedToFavoriteDate:Long?=null,
    var loadedAtDate:Long?=null
)