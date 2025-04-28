package com.example.myapplication.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Product

class CartViewModel : ViewModel() {
    private val _cartProducts = mutableStateOf<List<Product>>(emptyList())
    val cartProducts: List<Product> by _cartProducts

    fun addProduct(product: Product) {
        _cartProducts.value += product
    }

    fun removeProduct(barcode: String) {
        _cartProducts.value = _cartProducts.value.filterNot { it.barcode == barcode }
    }

    fun clearCart() {
        _cartProducts.value = emptyList()
    }
}
