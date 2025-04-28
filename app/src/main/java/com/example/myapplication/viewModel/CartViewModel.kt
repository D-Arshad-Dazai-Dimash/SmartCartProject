package com.example.myapplication.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val _scannedBarcodes = mutableStateOf<List<String>>(emptyList())

    val scannedBarcodes: State<List<String>> = _scannedBarcodes

    fun addBarcode(barcode: String) {
        _scannedBarcodes.value = _scannedBarcodes.value + barcode
    }

    fun clearCart() {
        _scannedBarcodes.value = emptyList()
    }
}
