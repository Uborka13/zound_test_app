package com.internal.data.local

import android.content.Context
import androidx.room.Room
import com.internal.data.local.dao.WatchListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "to_the_moon_room_database"

    @Singleton
    @Provides
    fun providesToTheMoonDatabase(@ApplicationContext context: Context): ToTheMoonDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToTheMoonDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesWatchListDao(database: ToTheMoonDatabase): WatchListDao {
        return database.getWatchListDao()
    }
}
