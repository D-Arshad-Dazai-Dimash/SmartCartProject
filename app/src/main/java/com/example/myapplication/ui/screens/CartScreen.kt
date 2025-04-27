package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CartScreen(navController: NavController, barcode: String?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Scanned Barcode: $barcode")
        // Here you can add your logic for displaying products, adding them to the cart, etc.
    }
}

