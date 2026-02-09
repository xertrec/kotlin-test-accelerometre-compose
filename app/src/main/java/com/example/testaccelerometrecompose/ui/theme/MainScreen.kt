package com.example.testaccelerometrecompose.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testaccelerometrecompose.MainViewModel
import com.example.testaccelerometrecompose.R

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val color by viewModel.color.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            SensorsColor(
                color = color,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun SensorsColor(
    color: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(if (color) Color.Red else Color.Green),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(),
        border = BorderStroke(10.dp, if (color) Color.Black else Color.LightGray)
    ) {
        Column {
            Text(text = "")
            Row {
                Text(text = "            ")
                Text(text = stringResource(R.string.shake))
            }
        }
    }
}
