package com.example.myapplication.ui.components.cartScreen

import com.example.myapplication.R
import com.example.myapplication.data.Product

fun getProductDetails(barcode: String): Product {
    return when (barcode) {
        "5449000276926" -> Product(
            barcode = barcode,
            name = "Fuse tea со вкусом ЯГОД 1л",
            price = 590.0,
            imageResId = R.drawable.fusetea
        )

        else -> Product(
            barcode = barcode,
            name = "Unknown Product",
            price = 0.0,
            imageResId = R.drawable.white_square
        )
    }
}
