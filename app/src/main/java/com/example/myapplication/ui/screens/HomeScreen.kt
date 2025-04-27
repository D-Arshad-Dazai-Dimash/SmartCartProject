package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.HomeScreen.ActionButtons
//import com.example.myapplication.ui.components.HomeScreen.BottomNavigationBar
import com.example.myapplication.ui.components.HomeScreen.Header
import com.example.myapplication.ui.components.HomeScreen.WelcomeMessage

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Scaffold(
        containerColor = Color.White,
//        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 46.dp,
                        start = 19.dp,
                        end = 19.dp
                    )
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                Header()
                Spacer(modifier = Modifier.height(24.dp))
                WelcomeMessage()
                Spacer(modifier = Modifier.height(24.dp))
                ActionButtons()
            }
        }
    )
}
