package com.yasser.data.genres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class GenreModel(
    val id: Int?=null,
    val name: String?=null,
):Parcelable