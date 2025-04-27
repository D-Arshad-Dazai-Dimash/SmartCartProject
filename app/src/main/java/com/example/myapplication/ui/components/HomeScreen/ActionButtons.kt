package com.example.myapplication.ui.components.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Preview(showBackground = true)
@Composable
fun ActionButtons() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(13.dp)

    ) {
        ActionCard(
            label = "Scan a product",
            count = "384 Scanner",
            isSwappable = true,
            pagerItems = listOf(
                "Page 1: 384 Scanner",
                "Page 2: Product Details",
                "Page 3: Next Step"
            ),
            backgroundImages = listOf(
                painterResource(id = R.drawable.ic_launcher_background), // Replace with actual image resources
                painterResource(id = R.drawable.image_icon3), // Replace with actual image resources
                painterResource(id = R.drawable.image_icon)  // Replace with actual image resources
            ),
            cardWidth = 370.dp,
            cardHeight = 150.dp
        )
        Row {
            ActionCard(
                label = "Receipt / Tax invoice",
                count = "23 detected",
                icon = painterResource(id = R.drawable.image_icon),
                cardWidth = 180.16.dp,
                cardHeight = 210.82.dp
            )
            ActionCard(
                label = "receipt / tax invoice",
                count = "23 detected",
                icon = painterResource(id = R.drawable.image_icon3),
                cardWidth = 180.16.dp,
                cardHeight =  210.82.dp
            )
        }
        ActionCard(
            label = "Products purchased",
            count = "164 purchases",
            icon = painterResource(id = R.drawable.image_icon2),
            cardWidth = 370.dp,
            cardHeight = 176.82.dp
        )
    }
}
