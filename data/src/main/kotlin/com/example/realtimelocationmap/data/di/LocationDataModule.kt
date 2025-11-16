package com.example.realtimelocationmap.data.di

import com.example.realtimelocationmap.data.location.FakeLocationRepository
import com.example.realtimelocationmap.domain.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationDataModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        impl: FakeLocationRepository
    ): LocationRepository
}
