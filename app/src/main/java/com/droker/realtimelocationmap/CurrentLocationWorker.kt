package com.droker.realtimelocationmap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.droker.realtimelocationmap.di.LocationWorkerEntryPoint
import com.droker.realtimelocationmap.domain.location.LocationPoint
import com.droker.realtimelocationmap.domain.location.LocationRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.EntryPointAccessors

class CurrentLocationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    // Hilt EntryPoint 통해 LocationRepository 주입
    private val locationRepository: LocationRepository by lazy {
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            LocationWorkerEntryPoint::class.java
        )
        entryPoint.locationRepository()
    }

    override suspend fun doWork(): Result {
        Log.d("CurrentLocationWorker", "doWork() 시작")

        val fineGranted = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {
            Log.e("CurrentLocationWorker", "위치 권한 없음")
            return Result.failure()
        }

        val fused = LocationServices.getFusedLocationProviderClient(applicationContext)

        val location = try {
            val loc = Tasks.await(fused.lastLocation)
            Log.d("CurrentLocationWorker", "lastLocation = $loc")
            loc
        } catch (e: Exception) {
            Log.e("CurrentLocationWorker", "lastLocation 에러", e)
            null
        }

        if (location == null) {
            Log.e("CurrentLocationWorker", "lastLocation 이 null, retry 호출")
            return Result.retry()
        }

        val point = LocationPoint(
            latitude = location.latitude,
            longitude = location.longitude
        )

        Log.d("CurrentLocationWorker", "위치 저장: $point")
        locationRepository.saveLocation(point)

        return Result.success()
    }
}