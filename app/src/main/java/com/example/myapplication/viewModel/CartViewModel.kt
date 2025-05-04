package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.components.product.Product
import com.google.firebase.database.*

class CartViewModel : ViewModel() {
    private val _cartProducts = mutableStateOf<List<Product>>(emptyList())
    val cartProducts: List<Product>
        get() = _cartProducts.value

    private val cartRef = FirebaseDatabase.getInstance().getReference("cart")

    private fun saveProductToFirebase(product: Product) {
        val productId = cartRef.push().key
        productId?.let {
            cartRef.child(it).setValue(product)
        }
    }

    fun addProduct(product: Product) {
        _cartProducts.value = _cartProducts.value + product
        saveProductToFirebase(product)
    }

    fun removeProduct(barcode: String) {
        _cartProducts.value = _cartProducts.value.filter { it.barcode != barcode }
        cartRef.orderByChild("barcode").equalTo(barcode)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.firstOrNull()?.key?.let { key ->
                        cartRef.child(key).removeValue()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun clearCart() {
        _cartProducts.value = emptyList()
        cartRef.removeValue()
    }

    fun loadCartFromFirebase() {
        cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                snapshot.children.forEach {
                    it.getValue(Product::class.java)?.let { product ->
                        products.add(product)
                    }
                }
                _cartProducts.value = products
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}

fun getProductDetailsFromFirebase(barcode: String, onResult: (Product?) -> Unit) {
    val dbRef = FirebaseDatabase.getInstance().getReference(barcode) // Где barcode - это ключ, например, "8887290101004"
    dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val product = snapshot.getValue(Product::class.java)
            onResult(product)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Firebase", "Error fetching product", error.toException())
            onResult(null)
        }
    })

}

