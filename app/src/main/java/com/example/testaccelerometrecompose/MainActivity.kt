package com.example.testaccelerometrecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.testaccelerometrecompose.ui.theme.TestAccelerometreComposeTheme

class MainActivity : ComponentActivity() {

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

    override fun onResume() {
        super.onResume()
        viewModel.startListening()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopListening()
    }
}
