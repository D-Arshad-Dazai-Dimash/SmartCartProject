package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.components.HomeScreen.ActionButtons
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.ui.components.HomeScreen.Header
import com.example.myapplication.ui.components.HomeScreen.WelcomeMessage
import com.example.myapplication.viewModel.ProfileViewModel

@Composable
fun HomeScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {

    val userName by profileViewModel.userName

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 46.dp, start = 19.dp, end = 19.dp)
                    .padding(paddingValues)
                    .background(color = Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                Header(navController = navController, userName = userName)
                Spacer(modifier = Modifier.height(24.dp))
                WelcomeMessage()
                Spacer(modifier = Modifier.height(24.dp))
                ActionButtons(navController = navController)
            }
        }
    )
}
