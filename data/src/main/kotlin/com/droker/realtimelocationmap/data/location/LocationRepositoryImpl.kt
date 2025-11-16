package com.droker.realtimelocationmap.data.location

import com.droker.realtimelocationmap.domain.location.LocationPoint
import com.droker.realtimelocationmap.domain.location.LocationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.abs

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {

    override fun observeLocations(): Flow<List<LocationPoint>> {
        return locationDao.observeLocations()
            .map { entities ->
                entities.map { e ->
                    LocationPoint(
                        latitude = e.latitude,
                        longitude = e.longitude
                    )
                }
            }
    }

    override suspend fun saveLocationIfChanged(point: LocationPoint) {
        val last = locationDao.getLastLocationOnce()

        if (last != null) {
            if (isSameLocation(last, point)) {
                // 🔹 같은 위치면 저장 안 함 (dedup)
                return
            }
        }

        val entity = LocationEntity(
            latitude = point.latitude,
            longitude = point.longitude,
            timestamp = System.currentTimeMillis()
        )
        locationDao.insertLocation(entity)
    }

    private fun isSameLocation(last: LocationEntity, newPoint: LocationPoint): Boolean {
        val latDiff = abs(last.latitude - newPoint.latitude)
        val lngDiff = abs(last.longitude - newPoint.longitude)

        // 대략 수 미터 수준 (필요하면 조정)
        val threshold = 1e-5

        return latDiff < threshold && lngDiff < threshold
    }
}