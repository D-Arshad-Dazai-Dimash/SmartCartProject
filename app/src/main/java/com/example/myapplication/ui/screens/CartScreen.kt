package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun CartScreen(navController: NavController,cartViewModel: CartViewModel) {
    val scannedBarcodes = cartViewModel.scannedBarcodes.value

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Scanned Barcodes:")

        if (scannedBarcodes.isEmpty()) {
            Text("No items in cart")
        } else {
            scannedBarcodes.forEach { barcode ->
                Text(text = "Scanned Barcode: $barcode", modifier = Modifier.padding(vertical = 4.dp))

            }
        }
    }
}


