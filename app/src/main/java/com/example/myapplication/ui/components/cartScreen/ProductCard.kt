package com.example.myapplication.ui.components.cartScreen//package com.example.myapplication.ui.components.cartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProductCard(
    barcode: String,
    productName: String,
    price: Double,
    quantity: Int,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle product click */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardColors(
            containerColor = Color(0x9C9898A4),
            contentColor = Color(0xFFF7F7F7),
            disabledContentColor = Color(0xFFF7F7F7),
            disabledContainerColor = Color(0x9C9898A4)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Gray, shape = CircleShape)
            ) {

            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productName,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: $${price}",
                    style = TextStyle(fontWeight = FontWeight.Medium),
                    color = Color.Black
                )
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Item",
                    tint = Color.Red
                )
            }
        }
    }
}

