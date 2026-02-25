package com.example.testaccelerometrecompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

// Clase dedicada a gestionar el acelerómetro, separando la lógica del sensor de la UI
class AccelerometerSensorManager(context: Context) {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Callback para enviar los datos al ViewModel
    private var onSensorValuesChanged: ((Triple<Float, Float, Float>) -> Unit)? = null

    // Propiedad para que el ViewModel pueda saber si el sensor existe
    val isSensorAvailable: Boolean
        get() = accelerometer != null

    // Función para obtener los detalles del sensor y mostrarlos en la UI
    fun getSensorInfo(): String {
        return if (isSensorAvailable) {
            "Name: ${accelerometer?.name}\n" +
            "Vendor: ${accelerometer?.vendor}\n" +
            "Version: ${accelerometer?.version}\n" +
            "Resolution: ${accelerometer?.resolution}\n" +
            "Power: ${accelerometer?.power} mA\n" +
            "Max Range: ${accelerometer?.maximumRange}"
        } else {
            "Accelerometer not available"
        }
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val values = Triple(event.values[0], event.values[1], event.values[2])
                // Cuando hay un nuevo dato, se lo pasamos al ViewModel
                onSensorValuesChanged?.invoke(values)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    fun setOnSensorValuesChangedListener(listener: (Triple<Float, Float, Float>) -> Unit) {
        onSensorValuesChanged = listener
    }

    fun startListening() {
        if (isSensorAvailable) {
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(sensorEventListener)
    }
}
