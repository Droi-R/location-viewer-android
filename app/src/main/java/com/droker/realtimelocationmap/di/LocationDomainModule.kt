package com.droker.realtimelocationmap.di

import com.droker.realtimelocationmap.domain.location.GetCurrentLocationUseCase
import com.droker.realtimelocationmap.domain.location.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationDomainModule {

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(
        locationRepository: LocationRepository
    ): GetCurrentLocationUseCase = GetCurrentLocationUseCase(locationRepository)
}
