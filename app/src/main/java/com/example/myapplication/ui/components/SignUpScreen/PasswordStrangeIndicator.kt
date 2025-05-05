package com.example.myapplication.ui.components.SignUpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PasswordStrengthIndicator(strength: Float, strengthText: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
    ) {
        LinearProgressIndicator(
            progress = strength,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray),
            color = when {
                strength <= 0.33f -> Color.Red
                strength in 0.34f..0.66f -> Color.Yellow
                else -> Color.Green
            },
        )

        Text(
            text = strengthText,
            color = when {
                strength <= 0.33f -> Color.Red
                strength in 0.34f..0.66f -> Color.Yellow
                else -> Color.Green
            },
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}