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

    private val _locations = MutableLiveData<List<LocationPoint>>(emptyList())
    val locations: LiveData<List<LocationPoint>> = _locations

    init {
        viewModelScope.launch {
            getCurrentLocationUseCase().collectLatest { points ->
                _locations.value = points
            }
        }
    }
}