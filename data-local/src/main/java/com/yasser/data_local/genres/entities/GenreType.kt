package com.yasser.data_local.genres.entities

import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
enum class GenreType {
    @SerialName("series")
    SERIES,
    @SerialName("movies")
    MOVIES
}