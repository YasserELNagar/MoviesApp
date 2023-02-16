package com.yasser.data_local.common.db

import android.app.Application
import androidx.room.Room
import com.yasser.data_local.movies.dao.MoviesDao
import com.yasser.data_local.common.Const.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DBModule {


    @Singleton
    @Provides
    fun provideAppDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(
            application,
            AppDataBase::class.java,
            DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(appDataBase: AppDataBase): MoviesDao {
        return appDataBase.moviesDao()
    }

}