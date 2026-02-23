package com.example.testaccelerometrecompose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accelerometerSensorManager = AccelerometerSensorManager(application)

    private val _accelerometerData = MutableStateFlow(Triple(0f, 0f, 0f))
    val accelerometerData = _accelerometerData.asStateFlow()

    private val _isAccelerometerAvailable = MutableStateFlow(false)
    val isAccelerometerAvailable = _isAccelerometerAvailable.asStateFlow()

    private val _accelerometerInfo = MutableStateFlow("")
    val accelerometerInfo = _accelerometerInfo.asStateFlow()

    init {
        _isAccelerometerAvailable.value = accelerometerSensorManager.isSensorAvailable
        if (accelerometerSensorManager.isSensorAvailable) {
            _accelerometerInfo.value = accelerometerSensorManager.getSensorInfo()
            accelerometerSensorManager.setOnSensorValuesChangedListener { values ->
                _accelerometerData.value = values
            }
        }
    }

    fun startListening() {
        accelerometerSensorManager.startListening()
    }

    fun stopListening() {
        accelerometerSensorManager.stopListening()
    }
}
