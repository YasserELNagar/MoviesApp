package com.yasser.data_local.movies.converter

import androidx.room.TypeConverter
import com.yasser.data_local.genres.entities.GenreEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GenreListConverter {

    @TypeConverter
    fun fromList(value : List<GenreEntity>?):String? = if (value==null) null else Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String?):List<GenreEntity>? = if (value==null) null else Json.decodeFromString<List<GenreEntity>>(value)
}