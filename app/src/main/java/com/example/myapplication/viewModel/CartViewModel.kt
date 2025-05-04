package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.components.product.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartViewModel : ViewModel() {
    private val _cartProducts = mutableStateOf<List<Product>>(emptyList())
    val cartProducts: List<Product>
        get() = _cartProducts.value

    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private val cartRef =
        FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}/carts")

    private fun saveProductToFirebase(product: Product) {
        val cartId = cartRef.push().key
        cartId?.let {
            val cartData = mapOf(
                "date" to System.currentTimeMillis(),
                "status" to "In Progress",
                "products" to mapOf(
                    product.barcode to mapOf(
                        "quantity" to product.quantity,
                        "price" to product.price
                    )
                )
            )
            cartRef.child(it).setValue(cartData)
        }
    }

    fun addProduct(product: Product) {
        val existingProduct = _cartProducts.value.find { it.barcode == product.barcode }

        if (existingProduct != null) {
            val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
            updateProductInFirebase(updatedProduct)
        } else {
            // Если товара нет в корзине, добавляем новый
            _cartProducts.value = _cartProducts.value + product
            saveProductToFirebase(product)
        }
    }

    private fun updateProductInFirebase(product: Product) {
        cartRef.orderByChild("products/${product.barcode}")
            .equalTo(product.barcode)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { cartSnapshot ->
                        val cartId = cartSnapshot.key
                        cartId?.let {
                            val productRef =
                                cartRef.child(it).child("products").child(product.barcode)
                            productRef.child("quantity").setValue(product.quantity)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
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
                snapshot.children.forEach { cartSnapshot ->
                    cartSnapshot.child("products").children.forEach { productSnapshot ->
                        val barcode = productSnapshot.key
                        barcode?.let {
                            getProductDetailsFromFirebase(it) { product ->
                                product?.let {
                                    products.add(it)
                                }
                            }
                        }
                    }
                }
                _cartProducts.value = products
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}


fun getProductDetailsFromFirebase(barcode: String, onResult: (Product?) -> Unit) {
    val dbRef = FirebaseDatabase.getInstance()
        .getReference("products/$barcode")
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
