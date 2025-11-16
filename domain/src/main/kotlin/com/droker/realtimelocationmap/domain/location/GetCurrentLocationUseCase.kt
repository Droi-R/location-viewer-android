package com.droker.realtimelocationmap.domain.location

import kotlinx.coroutines.flow.Flow

class GetCurrentLocationUseCase(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<LocationPoint>> {
        return locationRepository.observeLocations()
    }
}