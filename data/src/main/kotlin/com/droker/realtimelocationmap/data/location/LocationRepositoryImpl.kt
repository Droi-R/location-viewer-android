package com.droker.realtimelocationmap.data.location

import com.droker.realtimelocationmap.domain.location.LocationPoint
import com.droker.realtimelocationmap.domain.location.LocationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {

    override fun observeLocation(): Flow<LocationPoint?> {
        return locationDao.observeLocation().map { entity ->
            entity?.let {
                LocationPoint(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        }
    }

    override suspend fun saveLocation(point: LocationPoint) {
        val entity = LocationEntity(
            id = 0,
            latitude = point.latitude,
            longitude = point.longitude,
            timestamp = System.currentTimeMillis()
        )
        locationDao.insertLocation(entity)
    }
}