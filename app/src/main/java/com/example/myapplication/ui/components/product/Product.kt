package com.example.myapplication.ui.components.product

import com.google.mlkit.vision.barcode.common.Barcode

data class Product(
    val barcode : String = "",
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0
)
