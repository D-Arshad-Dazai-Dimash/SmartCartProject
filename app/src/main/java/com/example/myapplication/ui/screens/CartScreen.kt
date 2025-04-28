package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.cartScreen.ProductCard
import com.example.myapplication.viewModel.CartViewModel

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartProducts = cartViewModel.cartProducts

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            Text(text = "Your Cart", style = TextStyle(fontWeight = FontWeight.Medium))

            if (cartProducts.isEmpty()) {
                Text("No items in cart", style = TextStyle(fontWeight = FontWeight.Bold))
            } else {
                cartProducts.forEach { product ->
                    ProductCard(
                        barcode = product.barcode,
                        productName = product.name,
                        price = product.price,
                        idImage = product.imageResId,
                        onRemove = {
                            cartViewModel.removeProduct(product.barcode)
                        }
                    )
                }
            }
        }
    }
}

