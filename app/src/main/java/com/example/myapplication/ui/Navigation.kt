package com.example.myapplication.ui

import ProfileDataScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.PaymentFormPage
import com.example.myapplication.ui.screens.ReceiptPage
import com.example.myapplication.ui.screens.CartScreen
import com.example.myapplication.ui.screens.HistoryScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.NotificationsScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.ScanScreen
import com.example.myapplication.ui.screens.SignUpScreen
import com.example.myapplication.ui.screens.WelcomeScreen
import com.example.myapplication.viewModel.CartViewModel
import com.example.myapplication.viewModel.NotificationsViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {

    val cartViewModel: CartViewModel = viewModel()

    val isUserSignedIn = remember { false }

    val startDestination = if (isUserSignedIn) "home" else "welcome"

    NavHost(navController = navController, startDestination = "profile") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login") { LoginScreen(navController) } //
        composable("signup") { SignUpScreen(navController) } //
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

        composable("paymentForm/{formattedTotal}") { backStackEntry ->
            PaymentFormPage(
                navController,
                projectName = backStackEntry.arguments?.getString("formattedTotal") ?: ""
            )
        }
        composable("receipt/{projectName}/{amount}") { backStackEntry ->
            ReceiptPage(
                navController,
                projectName = backStackEntry.arguments?.getString("projectName") ?: "",
                amount = backStackEntry.arguments?.getString("amount") ?: ""
            )
        }

        composable("profile") { ProfileScreen(navController) }

        composable("personalData") { ProfileDataScreen(navController) }

        composable("notifications") {
            NotificationsScreen(
                navController,
                notificationsViewModel = NotificationsViewModel()
            )
        }
    }
}
