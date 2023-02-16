package com.yasser.data.movies.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.yasser.data.genres.model.GenreModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class MovieModel(
    val adult: Boolean?,
    val backdrop_path: String?,
    val id: Int?,
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
    val genres: List<GenreModel>?=null,
    val isFavorite:Boolean?=null,
    val loadedAtDate:Long?=null
):Parcelable