package com.droker.realtimelocationmap.data.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,   // 🔹 이제 자동 증가
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)