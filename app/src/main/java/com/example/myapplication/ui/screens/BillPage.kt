package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.viewModel.CartViewModel
@Composable
fun BillPage(navController: NavController, cartViewModel: CartViewModel) {
    val cartProducts = cartViewModel.cartProducts

    // Calculate the total
    val total: Double = cartProducts.sumOf { it.price * it.quantity }
    val formattedTotal = String.format("%.2f", total)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Add space at the top
            Spacer(modifier = Modifier.height(16.dp))  // This will create some space from the top

            // Store Info
            Text(
                text = "MAGNUM SHOPPING CENTER",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "TAX#01234567891234 (VAT Included)", fontSize = 14.sp)
            Text(text = "Tel 0123456789", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Receipt Title
            Text(
                text = "RECEIPT / TAX INVOICE",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Itemized list of purchased products
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                cartProducts.forEach { product ->
                    Text(
                        text = "${product.quantity} ${product.name} - ${product.price * product.quantity} TG",
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total section
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("$formattedTotal TG", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Store info at the bottom
            Text(
                text = "MAGNUM SHOPPING CENTER",
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "16/9/2566  9:41 p.m.",
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Back button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .background(Color(0xFFF5F5F5), CircleShape)
                .align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }
}
