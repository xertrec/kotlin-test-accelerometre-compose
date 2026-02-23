package com.example.testaccelerometrecompose

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val accelerometerData by viewModel.accelerometerData.collectAsState()
    val isAccelerometerAvailable by viewModel.isAccelerometerAvailable.collectAsState()
    val accelerometerInfo by viewModel.accelerometerInfo.collectAsState()

    var color by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(accelerometerData) {
        val (x, y, z) = accelerometerData
        if (abs(x) > 15 || abs(y) > 15 || abs(z) > 15) {
            color = !color
            Toast.makeText(context, "Shake detected!", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            // Top Area
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(if (color) Color.Red else Color.Green),
                border = BorderStroke(10.dp, if (color) Color.Black else Color.LightGray),
            ) {}

            // Middle Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isAccelerometerAvailable) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.shake),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = accelerometerInfo,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                } else {
                    Text(
                        text = "Sorry, there is no accelerometer",
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Bottom Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
        }
    }
}
