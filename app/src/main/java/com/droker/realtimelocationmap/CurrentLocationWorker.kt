package com.droker.realtimelocationmap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.droker.realtimelocationmap.di.LocationWorkerEntryPoint
import com.droker.realtimelocationmap.domain.location.LocationPoint
import com.droker.realtimelocationmap.domain.location.LocationRepository
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.EntryPointAccessors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
        val hasFine = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) return Result.failure()

        val fused = LocationServices.getFusedLocationProviderClient(applicationContext)

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000L
        ).setMaxUpdates(1).build()

        val location = suspendCoroutine<Location?> { cont ->
            fused.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    fused.removeLocationUpdates(this)
                    cont.resume(result.lastLocation)
                }
            }, Looper.getMainLooper())
        }

        if (location == null) {
            Log.e("Worker", "정확한 위치 얻기 실패 -> retry")
            return Result.retry()
        }

        val point = LocationPoint(location.latitude, location.longitude)
        locationRepository.saveLocationIfChanged(point)

        return Result.success()
    }
}