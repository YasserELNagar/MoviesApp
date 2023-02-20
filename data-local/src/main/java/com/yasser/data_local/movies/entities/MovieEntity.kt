package com.yasser.data_local.movies.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yasser.data_local.genres.entities.GenreEntity

@Keep
@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int?=null,
    val adult: Boolean?=null,
    val backdrop_path: String?=null,
    val original_language: String?=null,
    val original_title: String?=null,
    val overview: String?=null,
    val popularity: Double?=null,
    val poster_path: String?=null,
    val release_date: String?=null,
    val title: String?=null,
    val video: Boolean?=null,
    val vote_average: Double?=null,
    val vote_count: Int?=null,
    var genres: List<GenreEntity>?=null,
    var addedToFavoriteDate:Long?=null,
    var loadedAtDate:Long?=null
)