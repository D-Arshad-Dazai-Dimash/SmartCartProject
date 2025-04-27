package com.example.myapplication.ui

import ScanScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.CartScreen
import com.example.myapplication.ui.screens.HistoryScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.SignUpScreen
import com.example.myapplication.ui.screens.WelcomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {

    // Assume we check if the user is signed in or not
    val isUserSignedIn = remember { /* Retrieve this from SharedPreferences or Firebase */ true } // Replace with actual check

    // Start the NavHost based on whether the user is signed in or not
    val startDestination = if (isUserSignedIn) "home" else "welcome"

    NavHost(navController = navController, startDestination = startDestination) {
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
                onBarcodeScanned = { barcode ->
                    println("Scanned barcode: $barcode")
                    // Navigate to Cart, and clear previous screens on the stack
                    navController.navigate("cart/$barcode") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true // Prevents pushing the same screen again
                    }
                })
        }
        composable("cart/{barcode}") { backStackEntry ->
            val barcode = backStackEntry.arguments?.getString("barcode")
            CartScreen(navController = navController, barcode = barcode)
        }
    }
}
