package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.Screen


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.Home, Screen.Scan, Screen.Cart, Screen.History)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                clip = false // Don't clip the shadow
            )
            .background(Color.White) // Ensure the background is white
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), // Adjust padding if necessary
            containerColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { screen ->
                NavigationBarItem(
                    selected = currentRoute == screen.route,
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.title,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFFBBBBBB),
                        selectedIconColor = Color(0xFF2DC0FF),
                        unselectedTextColor = Color(0xFFBBBBBB),
                        selectedTextColor = Color(0xFF2DC0FF),
                        indicatorColor = Color(0xFFD0EDFB).copy(0.8f)
                    )
                )
            }
        }
    }
}
