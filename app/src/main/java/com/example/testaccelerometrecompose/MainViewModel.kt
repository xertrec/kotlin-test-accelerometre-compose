package com.example.testaccelerometrecompose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accelerometerSensorManager = AccelerometerSensorManager(application)
    private val lightSensorManager = LightSensorManager(application)

    // Accelerometer State
    private val _accelerometerData = MutableStateFlow(Triple(0f, 0f, 0f))
    val accelerometerData = _accelerometerData.asStateFlow()
    private val _isAccelerometerAvailable = MutableStateFlow(false)
    val isAccelerometerAvailable = _isAccelerometerAvailable.asStateFlow()
    private val _accelerometerInfo = MutableStateFlow("")
    val accelerometerInfo = _accelerometerInfo.asStateFlow()

    // Light Sensor State
    private val _lightSensorData = MutableStateFlow(0f)
    val lightSensorData = _lightSensorData.asStateFlow()
    private val _isLightSensorAvailable = MutableStateFlow(false)
    val isLightSensorAvailable = _isLightSensorAvailable.asStateFlow()
    private val _lightSensorInfo = MutableStateFlow("")
    val lightSensorInfo = _lightSensorInfo.asStateFlow()
    private val _lightThresholds = MutableStateFlow<Pair<Float, Float>?>(null)
    val lightThresholds = _lightThresholds.asStateFlow()

    init {
        // Accelerometer setup
        _isAccelerometerAvailable.value = accelerometerSensorManager.isSensorAvailable
        if (accelerometerSensorManager.isSensorAvailable) {
            _accelerometerInfo.value = accelerometerSensorManager.getSensorInfo()
            accelerometerSensorManager.setOnSensorValuesChangedListener { values ->
                _accelerometerData.value = values
            }
        }

        // Light sensor setup
        _isLightSensorAvailable.value = lightSensorManager.isSensorAvailable
        if (lightSensorManager.isSensorAvailable) {
            _lightSensorInfo.value = lightSensorManager.getSensorInfo()
            _lightThresholds.value = lightSensorManager.getThresholds()
            lightSensorManager.setOnSensorValuesChangedListener { value ->
                _lightSensorData.value = value
            }
        }
    }

    fun startListening() {
        accelerometerSensorManager.startListening()
        lightSensorManager.startListening()
    }

    fun stopListening() {
        accelerometerSensorManager.stopListening()
        lightSensorManager.stopListening()
    }
}
