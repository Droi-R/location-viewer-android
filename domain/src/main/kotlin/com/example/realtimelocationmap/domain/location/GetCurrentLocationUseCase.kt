package com.example.realtimelocationmap.domain.location

class GetCurrentLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LocationPoint? {
        return locationRepository.getCurrentLocation()
    }
}
