package com.droker.realtimelocationmap.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droker.realtimelocationmap.domain.location.GetCurrentLocationUseCase
import com.droker.realtimelocationmap.domain.location.LocationPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _location = MutableLiveData<LocationPoint?>()
    val location: LiveData<LocationPoint?> = _location

    init {
        // Room DB를 계속 구독해서 최신 위치 반영
        viewModelScope.launch {
            getCurrentLocationUseCase().collectLatest { point ->
                _location.value = point
            }
        }
    }
}
