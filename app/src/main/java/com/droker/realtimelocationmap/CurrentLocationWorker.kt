package com.droker.realtimelocationmap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.droker.realtimelocationmap.domain.location.LocationPoint
import com.droker.realtimelocationmap.domain.location.LocationRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CurrentLocationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val locationRepository: LocationRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // 권한 체크
        val fineGranted = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {
            // 권한 없으면 실패 처리
            return Result.failure()
        }

        val fused = LocationServices.getFusedLocationProviderClient(applicationContext)

        val location = try {
            // lastLocation 은 nullable
            Tasks.await(fused.lastLocation)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (location == null) {
            // 위치 못 얻었으면 나중에 다시 시도
            return Result.retry()
        }

        val point = LocationPoint(
            latitude = location.latitude,
            longitude = location.longitude
        )

        // Room DB 저장
        locationRepository.saveLocation(point)

        return Result.success()
    }
}