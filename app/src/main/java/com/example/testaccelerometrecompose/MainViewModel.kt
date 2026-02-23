package com.example.testaccelerometrecompose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _color: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val color = _color.asStateFlow()

    init {
        val sensorManager = AccelerometerSensorManager(this)
    }
}