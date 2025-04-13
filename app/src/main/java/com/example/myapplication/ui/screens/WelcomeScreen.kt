package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.PutBackGroundImage
import kotlinx.coroutines.delay


//@Preview(showBackground = true)
@Composable
fun WelcomeScreen(navController: NavController) {
//fun WelcomeScreen(){
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("signup")
    }
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .systemBarsPadding()
            .consumeWindowInsets(WindowInsets.systemBars)
    ) {
        PutBackGroundImage(R.drawable.welcome)
    }
}