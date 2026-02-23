package com.example.testaccelerometrecompose

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _color: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val color = _color.asStateFlow()

    init {
        val sensorManager = AccelerometerSensorManager(application)
    }
}