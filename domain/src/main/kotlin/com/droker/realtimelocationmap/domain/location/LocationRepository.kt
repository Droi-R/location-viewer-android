package com.droker.realtimelocationmap.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    /** Room DB에 저장된 최신 위치 스트림 */
    fun observeLocation(): Flow<LocationPoint?>

    /** Room DB에 위치 저장 (백그라운드 Worker에서 호출) */
    suspend fun saveLocation(point: LocationPoint)
}
