package com.example.myapplication.ui.components.HomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun WelcomeMessage() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "WELCOME TO",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF363636),
                letterSpacing = 3.sp

            )
        )
        Row {
            Text(
                text = " smart",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                )
            )
            Text(
                text = " shopping",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF08F5F)
                )
            )
            Text(
                text = " cart",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                )
            )
        }

    }
}