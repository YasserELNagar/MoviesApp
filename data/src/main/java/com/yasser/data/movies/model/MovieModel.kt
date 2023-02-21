package com.yasser.data.movies.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.yasser.data.genres.model.GenreModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class MovieModel(
    val adult: Boolean?=null,
    val backdrop_path: String?=null,
    val id: Int?=null,
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
    val genres: List<GenreModel>?=null,
    val isFavorite:Boolean?=null,
    val loadedAtDate:Long?=null
):Parcelable