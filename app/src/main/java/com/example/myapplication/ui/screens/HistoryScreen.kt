package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R
import com.example.myapplication.ui.components.BottomNavigationBar


@Composable
fun HistoryScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .size(45.dp, 44.dp)
                        .clickable {
                            navController.popBackStack("home", inclusive = false)
                        }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(paddingValues)
                ) {

                    Spacer(modifier = Modifier.height(28.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "History", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Order completed", fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val orders = listOf(1, 2, 3) // Example list of orders
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(orders) {
                            OrderCard()
                        }
                    }
                }
            } })
}

@Composable
fun OrderCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF00C853), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp).width(70.dp)
                ) {
                    Text(text = "Paid", color = Color.White, fontSize = 12.sp)
                }

                Text(text = "2146 ₸", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "4 May (17:35)", fontSize = 12.sp, color = Color.Gray)
            Text(text = "Almaty, Kazakhstan", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Image(painterResource(id = R.drawable.bread), contentDescription = "Product", modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Image(painterResource(id = R.drawable.fusetea), contentDescription = "Product", modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Image(painterResource(id = R.drawable.eggs), contentDescription = "Product", modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Image(painterResource(id = R.drawable.bon_aqua), contentDescription = "Product", modifier = Modifier.size(32.dp))
            }
        }
    }
}
