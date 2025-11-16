package com.example.realtimelocationmap.domain.location

interface LocationRepository {
    suspend fun getCurrentLocation(): LocationPoint?
}
