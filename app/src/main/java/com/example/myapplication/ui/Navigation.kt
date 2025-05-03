package com.example.myapplication.ui

import ScanScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.CartScreen
import com.example.myapplication.ui.screens.HistoryScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.SignUpScreen
import com.example.myapplication.ui.screens.WelcomeScreen
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {

    val cartViewModel: CartViewModel = viewModel()

    val isUserSignedIn = remember { false }

    val startDestination = if (isUserSignedIn) "home" else "home"

    NavHost(navController = navController, startDestination = "home") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("signup") { SignUpScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("history") { HistoryScreen(navController) }

        composable("scan") {
            ScanScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }
    }
}
