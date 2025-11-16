package com.droker.realtimelocationmap.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    /** 저장된 위치 히스토리 전체를 시간순으로 흘려줌 */
    fun observeLocations(): Flow<List<LocationPoint>>

    /** 마지막 위치와 비교해서 달라졌을 때만 새로 저장 */
    suspend fun saveLocationIfChanged(point: LocationPoint)
}