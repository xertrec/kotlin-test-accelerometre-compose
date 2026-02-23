package com.example.testaccelerometrecompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerSensorManager(context: Context) {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var onSensorValuesChanged: ((Triple<Float, Float, Float>) -> Unit)? = null

    val isSensorAvailable: Boolean
        get() = accelerometer != null

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
