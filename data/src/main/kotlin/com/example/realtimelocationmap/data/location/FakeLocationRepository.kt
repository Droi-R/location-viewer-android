package com.example.realtimelocationmap.data.location

import com.example.realtimelocationmap.domain.location.LocationPoint
import com.example.realtimelocationmap.domain.location.LocationRepository
import javax.inject.Inject

class FakeLocationRepository @Inject constructor() : LocationRepository {

    override suspend fun getCurrentLocation(): LocationPoint? {
        // TODO 실제 위치 구현으로 교체
        return LocationPoint(
            latitude = 37.5665,   // 서울 근처
            longitude = 126.9780
        )
    }
}
