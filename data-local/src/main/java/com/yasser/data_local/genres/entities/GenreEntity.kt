package com.yasser.data_local.genres.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.serialization.Serializable
@Keep
@Entity(tableName = "Genres")
data class GenreEntity(
    @PrimaryKey
    val id: Int?=null,
    val name: String?=null,
    var type: GenreType?=null,
)