package com.droker.realtimelocationmap.data.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    // 🔹 전체 히스토리 관찰 (시간순)
    @Query("SELECT * FROM location ORDER BY timestamp ASC")
    fun observeLocations(): Flow<List<LocationEntity>>

    // 🔹 가장 마지막 위치를 한 번만 가져오기 (저장 시 비교용)
    @Query("SELECT * FROM location ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastLocationOnce(): LocationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(entity: LocationEntity)
}