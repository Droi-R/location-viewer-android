package com.droker.realtimelocationmap.di

import com.droker.realtimelocationmap.domain.location.LocationRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocationWorkerEntryPoint {
    fun locationRepository(): LocationRepository
}