package com.droker.realtimelocationmap.domain.location

import kotlinx.coroutines.flow.Flow


class GetCurrentLocationUseCase(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<LocationPoint?> {
        return locationRepository.observeLocation()
    }
}
