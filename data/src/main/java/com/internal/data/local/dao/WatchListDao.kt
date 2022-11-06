package com.internal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.internal.data.local.entities.WatchListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertWatchListEntity(watchListEntity: WatchListEntity)

    @Query("DELETE FROM watch_list_table WHERE symbol = :symbol")
    suspend fun deleteWatchListEntity(symbol: String)

    @Query("SELECT * FROM watch_list_table ORDER BY inserted_at DESC")
    fun getWatchList(): Flow<List<WatchListEntity>>
}
