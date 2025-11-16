package com.droker.realtimelocationmap.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droker.realtimelocationmap.data.location.LocationDao
import com.droker.realtimelocationmap.data.location.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}