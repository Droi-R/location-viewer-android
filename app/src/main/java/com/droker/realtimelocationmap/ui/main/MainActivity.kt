package com.droker.realtimelocationmap.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.droker.realtimelocationmap.CurrentLocationWorker
import com.droker.realtimelocationmap.databinding.ActivityMainBinding
import com.droker.realtimelocationmap.domain.location.LocationPoint
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var naverMap: NaverMap? = null
    private val markers = mutableListOf<Marker>()
    private var latestLocation: LocationPoint? = null

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                // 권한 허용 후: 마지막 위치로 이동 + Worker 실행
                moveCameraToLatest()
                enqueueLocationWork()
            } else {
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMapFragment()
        setupUi()
        observeLocations()
    }

    private fun setupMapFragment() {
        val fm = supportFragmentManager
        var mapFragment = fm.findFragmentById(binding.mapFragment.id) as MapFragment?
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction()
                .add(binding.mapFragment.id, mapFragment)
                .commit()
        }
        mapFragment.getMapAsync(this)
    }

    private fun setupUi() {
        binding.btnCurrentLocation.setOnClickListener {
            if (hasLocationPermission()) {
                // 1) 기존에 저장된 마지막 위치로 카메라 이동 (DB 변화 없어도)
                moveCameraToLatest()
                // 2) Worker 돌려서 실제 현재 위치 확인 + 변경 시 DB 저장
                enqueueLocationWork()
            } else {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun observeLocations() {
        viewModel.locations.observe(this) { points ->
            latestLocation = points.lastOrNull()

            val map = naverMap ?: return@observe
            updateMarkers(map, points)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        // 앱 처음 켰을 때 이미 DB에 데이터가 있다면 바로 복원
        latestLocation?.let { last ->
            val list = viewModel.locations.value.orEmpty()
            updateMarkers(naverMap, list)
            moveCameraTo(last)
        }
    }

    private fun updateMarkers(map: NaverMap, points: List<LocationPoint>) {
        // 기존 마커 제거
        markers.forEach { it.map = null }
        markers.clear()

        // 히스토리 전체 마커 다시 그림
        points.forEach { p ->
            val m = Marker().apply {
                position = LatLng(p.latitude, p.longitude)
                this.map = map
            }
            markers.add(m)
        }
    }

    private fun moveCameraToLatest() {
        val map = naverMap ?: return
        val last = latestLocation ?: return
        moveCameraTo(last, map)
    }

    private fun moveCameraTo(point: LocationPoint, map: NaverMap? = naverMap) {
        val m = map ?: return
        val pos = LatLng(point.latitude, point.longitude)
        m.moveCamera(CameraUpdate.scrollTo(pos))
    }

    private fun enqueueLocationWork() {
        val request = OneTimeWorkRequestBuilder<CurrentLocationWorker>()
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }
}