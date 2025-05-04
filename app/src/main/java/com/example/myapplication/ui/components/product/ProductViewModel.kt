package com.example.myapplication.ui.components.product

import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.example.myapplication.data.Product

class ProductViewModel : ViewModel() {

    private val dbRef = FirebaseDatabase.getInstance().getReference("products")

    fun addProduct(product: Product) {
        // Используем barcode как ключ
        dbRef.child(product.barcode).setValue(product)
    }

    fun getProducts(onResult: (List<Product>) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<Product>()
                for (child in snapshot.children) {
                    val product = child.getValue(Product::class.java)
                    product?.let { productList.add(it) }
                }
                onResult(productList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }

    fun deleteProduct(barcode: String) {
        dbRef.child(barcode).removeValue()
    }

    fun updateProduct(product: Product) {
        dbRef.child(product.barcode).setValue(product)
    }
}
