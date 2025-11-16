package com.droker.realtimelocationmap.ui.main



import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.droker.realtimelocationmap.databinding.ActivityMainBinding
import com.droker.realtimelocationmap.CurrentLocationWorker
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.widget.Toast

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
    private var marker: Marker? = null

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                enqueueLocationWork()
            } else {
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMapFragment()
        setupUi()
        observeLocation()
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

    private fun setupUi() {
        binding.btnCurrentLocation.setOnClickListener {
            if (hasLocationPermission()) {
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

    private fun enqueueLocationWork() {
        val request = OneTimeWorkRequestBuilder<CurrentLocationWorker>()
            .build()

        WorkManager.getInstance(this).enqueue(request)
    }

    private fun observeLocation() {
        viewModel.location.observe(this) { point ->
            val map = naverMap ?: return@observe
            val p = point ?: return@observe

            val position = LatLng(p.latitude, p.longitude)

            if (marker == null) {
                marker = Marker().apply {
                    this.position = position
                    this.map = map
                }
            } else {
                marker?.position = position
            }

            map.moveCamera(CameraUpdate.scrollTo(position))
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        // 필요하면 UI 설정, 위치 레이어 등 여기서 추가
        // naverMap.uiSettings.isLocationButtonEnabled = false (우리는 커스텀 버튼 사용)
    }
}
