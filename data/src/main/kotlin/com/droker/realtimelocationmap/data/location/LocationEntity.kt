package com.droker.realtimelocationmap.data.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Int = 0,   // 항상 0 한 row만 유지
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)