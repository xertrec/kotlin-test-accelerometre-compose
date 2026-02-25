package com.example.testaccelerometrecompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

// Clase dedicada a gestionar el sensor de luz, aislando su lógica
class LightSensorManager(context: Context) {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val lightSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    // Callback para notificar al ViewModel cuando hay un nuevo dato válido
    private var onSensorValuesChanged: ((Float) -> Unit)? = null

    // Variables para el filtrado de datos (evitamos la sobrecarga de la UI)
    private var lastValue: Float = 0f
    private var lastUpdateTime: Long = 0

    // Propiedad para que el ViewModel sepa si el sensor está disponible en el dispositivo
    val isSensorAvailable: Boolean
        get() = lightSensor != null

    // Función para obtener la información del sensor que se mostrará en la UI
    fun getSensorInfo(): String {
        return if (isSensorAvailable) {
            "Max Range: ${lightSensor?.maximumRange} lxs"
        } else {
            "Light sensor not available"
        }
    }

    // Calculamos los umbrales de 1/3 y 2/3 del rango máximo
    fun getThresholds(): Pair<Float, Float>? {
        return if (isSensorAvailable) {
            val maxRange = lightSensor?.maximumRange ?: 0f
            Pair(maxRange / 3, maxRange * 2 / 3)
        } else {
            null
        }
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
                val currentValue = event.values[0]
                val currentTime = System.currentTimeMillis()

                // Aplicamos el filtrado para cumplir con las buenas prácticas:
                // solo actualizamos si ha pasado 1 segundo Y el cambio de luz es > 200 lxs.
                if (currentTime - lastUpdateTime > 1000 && abs(currentValue - lastValue) > 200) {
                    lastValue = currentValue
                    lastUpdateTime = currentTime
                    onSensorValuesChanged?.invoke(currentValue)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    fun setOnSensorValuesChangedListener(listener: (Float) -> Unit) {
        onSensorValuesChanged = listener
    }

    fun startListening() {
        if (isSensorAvailable) {
            sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(sensorEventListener)
    }
}
