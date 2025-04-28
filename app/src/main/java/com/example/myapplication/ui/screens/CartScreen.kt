package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val scannedBarcodes = cartViewModel.scannedBarcodes.value
    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) },
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Scanned Barcodes:")

                    if (scannedBarcodes.isEmpty()) {
                        Text("No items in cart")
                    } else {
                        scannedBarcodes.forEach { barcode ->
                            Text(
                                text = "Scanned Barcode: $barcode",
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                        }
                    }
                }
            }
        })
}
