package com.internal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.internal.data.local.dao.WatchListDao
import com.internal.data.local.entities.WatchListEntity

@Database(
    entities = [WatchListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToTheMoonDatabase : RoomDatabase() {
    abstract fun getWatchListDao(): WatchListDao
}
