package com.example.testaccelerometrecompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

class LightSensorManager(context: Context) {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val lightSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    private var onSensorValuesChanged: ((Float) -> Unit)? = null

    private var lastValue: Float = 0f
    private var lastUpdateTime: Long = 0

    val isSensorAvailable: Boolean
        get() = lightSensor != null

    fun getSensorInfo(): String {
        return if (isSensorAvailable) {
            "Max Range: ${lightSensor?.maximumRange} lxs"
        } else {
            "Light sensor not available"
        }
    }

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
