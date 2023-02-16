package com.yasser.data_local.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yasser.data_local.movies.dao.MoviesDao
import com.yasser.data_local.movies.entities.MovieEntity
import com.yasser.data_local.common.Const.DB_VERSION
import com.yasser.data_local.common.converter.IntListConverter
import com.yasser.data_local.genres.dao.GenresDao
import com.yasser.data_local.movies.converter.GenreListConverter
import com.yasser.data_local.genres.entities.GenreEntity

@TypeConverters(IntListConverter::class, GenreListConverter::class)
@Database(entities = [MovieEntity::class, GenreEntity::class], version = DB_VERSION, exportSchema = false)
abstract class AppDataBase :RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun genresDao(): GenresDao
}