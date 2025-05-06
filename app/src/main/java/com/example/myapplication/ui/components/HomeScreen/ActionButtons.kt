package com.example.myapplication.ui.components.HomeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R

//@Preview(showBackground = true)
@Composable
fun ActionButtons(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
//            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                painterResource(id = R.drawable.dis),
                painterResource(id = R.drawable.magnum_icecream),
                painterResource(id = R.drawable.magnun)
            ),
            cardWidth = 370.dp,
            cardHeight = 150.dp,
            navController = navController,
            destination = "home"
        )
        Row {
            ActionCard(
                label = "Scan a product",
                count = "384 detected",
                icon = painterResource(id = R.drawable.image_icon),
                cardWidth = 180.16.dp,
                cardHeight = 210.82.dp,
                navController = navController,
                destination = "scan"
            )
            ActionCard(
                label = "receipt / tax invoice",
                count = "23 detected",
                icon = painterResource(id = R.drawable.image_icon3),
                cardWidth = 180.16.dp,
                cardHeight = 210.82.dp,
                navController = navController,
                destination = "home"
            )
        }
        ActionCard(
            label = "Products purchased",
            count = "164 purchases",
            icon = painterResource(id = R.drawable.image_icon2),
            cardWidth = 370.dp,
            cardHeight = 176.82.dp,
            navController = navController,
            destination = "home"
        )
    }
}