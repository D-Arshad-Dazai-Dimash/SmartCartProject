package com.example.myapplication.ui

import com.example.myapplication.R


sealed class Screen(val route: String, val title: String, val icon: Int) {
    object Home : Screen("home", "Home",  R.drawable.bottom_icon)
    object History : Screen("history", "History",  R.drawable.bottom_icon3)
    object Scan : Screen("scan", "Scan", R.drawable.bottom_icon2)
    object Cart : Screen("cart", "Cart", R.drawable.bottom_icon4)
}
