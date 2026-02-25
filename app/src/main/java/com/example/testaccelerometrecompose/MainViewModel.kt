package com.example.testaccelerometrecompose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// El ViewModel separa la lógica de los datos de la interfaz (MVVM)
// También ayuda a que los datos no se pierdan si rotamos la pantalla
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accelerometerSensorManager = AccelerometerSensorManager(application)
    private val lightSensorManager = LightSensorManager(application)

    // Usamos StateFlow para que la pantalla se actualice sola cuando cambien los datos
    private val _accelerometerData = MutableStateFlow(Triple(0f, 0f, 0f))
    val accelerometerData = _accelerometerData.asStateFlow()

    private val _isAccelerometerAvailable = MutableStateFlow(false)
    val isAccelerometerAvailable = _isAccelerometerAvailable.asStateFlow()

    private val _accelerometerInfo = MutableStateFlow("")
    val accelerometerInfo = _accelerometerInfo.asStateFlow()

    private val _lightSensorData = MutableStateFlow(0f)
    val lightSensorData = _lightSensorData.asStateFlow()

    private val _isLightSensorAvailable = MutableStateFlow(false)
    val isLightSensorAvailable = _isLightSensorAvailable.asStateFlow()

    private val _lightSensorInfo = MutableStateFlow("")
    val lightSensorInfo = _lightSensorInfo.asStateFlow()

    private val _lightThresholds = MutableStateFlow<Pair<Float, Float>?>(null)
    val lightThresholds = _lightThresholds.asStateFlow()

    init {
        // Miramos si el acelerómetro está disponible al arrancar
        _isAccelerometerAvailable.value = accelerometerSensorManager.isSensorAvailable
        if (accelerometerSensorManager.isSensorAvailable) {
            _accelerometerInfo.value = accelerometerSensorManager.getSensorInfo()
            accelerometerSensorManager.setOnSensorValuesChangedListener { values ->
                _accelerometerData.value = values
            }
        }

        // Lo mismo para el sensor de luz
        _isLightSensorAvailable.value = lightSensorManager.isSensorAvailable
        if (lightSensorManager.isSensorAvailable) {
            _lightSensorInfo.value = lightSensorManager.getSensorInfo()
            _lightThresholds.value = lightSensorManager.getThresholds()
            lightSensorManager.setOnSensorValuesChangedListener { value ->
                _lightSensorData.value = value
            }
        }
    }

    // Funciones para encender/apagar sensores desde la Activity (mejoramos batería)
    fun startListening() {
        accelerometerSensorManager.startListening()
        lightSensorManager.startListening()
    }

    fun stopListening() {
        accelerometerSensorManager.stopListening()
        lightSensorManager.stopListening()
    }
}
