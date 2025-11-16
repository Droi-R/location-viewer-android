package com.droker.realtimelocationmap.data.di

import android.content.Context
import androidx.room.Room
import com.droker.realtimelocationmap.data.db.AppDatabase
import com.droker.realtimelocationmap.data.location.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "location-db"
        ).build()

    @Provides
    @Singleton
    fun provideLocationDao(
        db: AppDatabase
    ): LocationDao = db.locationDao()
}