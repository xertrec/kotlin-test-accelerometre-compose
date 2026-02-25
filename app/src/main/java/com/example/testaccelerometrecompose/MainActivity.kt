package com.example.testaccelerometrecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.testaccelerometrecompose.ui.theme.TestAccelerometreComposeTheme

class MainActivity : ComponentActivity() {

    // Instancia del ViewModel siguiendo la arquitectura MVVM
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAccelerometreComposeTheme {
                MainScreen(
                    viewModel = viewModel
                )
            }
        }
    }

    // Gestión del ciclo de vida: registramos los sensores solo cuando la app está activa
    override fun onResume() {
        super.onResume()
        viewModel.startListening()
    }

    // Muy importante: cancelamos el registro al pausar para evitar consumo innecesario de batería
    override fun onPause() {
        super.onPause()
        viewModel.stopListening()
    }
}
