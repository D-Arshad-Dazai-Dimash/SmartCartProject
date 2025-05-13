package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.cartScreen.ProductCard
import com.example.myapplication.viewModel.CartViewModel
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartProducts = cartViewModel.cartProducts

    LaunchedEffect(Unit) {
        cartViewModel.loadCartFromFirebase()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF5F5F5), CircleShape)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Shopping Cart 🛒",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cartProducts.isEmpty()) {
            Text("No items in cart", fontSize = 16.sp)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                cartProducts.forEach { product ->
                    val imageRes = when (product.barcode) {
                        "1234567890" -> R.drawable.maccoffee
                        "4870202521186" -> R.drawable.import_files_aksi
                        "5449000189325" -> R.drawable.ftpeach
                        else -> R.drawable.bon_aqua
                    }

                    ProductCard(
                        imageRes = imageRes,
                        productName = product.name,
                        price = product.price,
                        quantity = product.quantity,
                        barcode = product.barcode,
                        onIncrease = { cartViewModel.increaseQuantity(product.barcode) },
                        onDecrease = { cartViewModel.decreaseQuantity(product.barcode) },
                        onRemove = {
                            cartViewModel.removeProduct(product.barcode)
                        }                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            val total: Double = cartProducts.sumOf { it.price * it.quantity }
            val formattedTotal = String.format("%.2f", total)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("$formattedTotal TG", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF5722))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("paymentForm/$formattedTotal") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7A00)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Go To Payment", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
