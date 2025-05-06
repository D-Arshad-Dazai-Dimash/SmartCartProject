package com.example.myapplication.ui

import ProfileDataScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.BillPage
import com.example.myapplication.ui.screens.PaymentFormPage
import com.example.myapplication.ui.screens.ReceiptPage
import com.example.myapplication.ui.screens.CartScreen
import com.example.myapplication.ui.screens.HistoryScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.NotificationsScreen
import com.example.myapplication.ui.screens.OrderDetailsScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.ScanScreen
import com.example.myapplication.ui.screens.SignUpScreen
import com.example.myapplication.ui.screens.WelcomeScreen
import com.example.myapplication.viewModel.CartViewModel
import com.example.myapplication.viewModel.NotificationsViewModel
import com.example.myapplication.viewModel.ProfileViewModel
@Composable
fun SetupNavGraph(navController: NavHostController) {

    val cartViewModel: CartViewModel = viewModel()
    val isUserSignedIn = remember { false }

    val startDestination = if (isUserSignedIn) "home" else "welcome"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("home") { HomeScreen(navController, ProfileViewModel()) }
        composable("history") { HistoryScreen(navController, cartViewModel) }
        composable("scan") {
            ScanScreen(navController = navController, cartViewModel = cartViewModel)
        }
        composable("billPage") {
            BillPage(navController = navController, cartViewModel = cartViewModel)

        }

        composable("cart") {
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }
        composable("paymentForm/{formattedTotal}") { backStackEntry ->
            val formattedTotal = backStackEntry.arguments?.getString("formattedTotal") ?: ""
            PaymentFormPage(
                navController = navController,
                amount = formattedTotal,
                cartViewModel = cartViewModel
            )
        }

        composable("receipt/{amount}/{orderId}") { backStackEntry ->
            val amount = backStackEntry.arguments?.getString("amount")
            val orderId = backStackEntry.arguments?.getString("orderId")

            if (amount.isNullOrEmpty() || orderId.isNullOrEmpty()) {
                Log.e("Navigation", "Amount or OrderId is null or empty!")
                navController.popBackStack()
            } else {
                Log.d("Navigation", "Navigating to ReceiptPage with amount: $amount and orderId: $orderId")
                ReceiptPage(navController, amount = amount, orderId = orderId)  // Передаем оба параметра
            }
        }






        composable("orderDetails/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderDetailsScreen(orderId = orderId, cartViewModel = cartViewModel, navController = navController)
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

