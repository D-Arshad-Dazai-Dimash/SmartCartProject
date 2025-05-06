
package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.components.product.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Order(
    val orderId: String = "",
    val totalAmount: Double = 0.0,
    val date: String = "",       // Дата заказа
    val orderTime: String = "",  // Время, когда пользователь нажал Pay Now
    val products: List<Product> = emptyList()
)

class CartViewModel : ViewModel() {
    private val _cartProducts = mutableStateOf<List<Product>>(emptyList())
    val cartProducts: List<Product>
        get() = _cartProducts.value

    private val _orderHistory = mutableStateOf<List<Order>>(emptyList())
    val orderHistory: List<Order>
        get() = _orderHistory.value

    private fun getUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user"
    }

    private fun getCartRef(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("users").child(getUserId()).child("cart")
    }

    private fun getOrderHistoryRef(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("users").child(getUserId()).child("orderHistory")
    }

    private fun saveProductToFirebase(product: Product) {
        val productId = getCartRef().push().key
        productId?.let {
            getCartRef().child(it).setValue(product)
        }
    }


    private fun saveOrderToFirebase(order: Order) {
        val orderId = getOrderHistoryRef().push().key
        orderId?.let {
            getOrderHistoryRef().child(it).setValue(order)
        }
    }

    // Метод для сохранения заказа
    fun saveOrder(totalAmount: Double, cartProducts: List<Product>, date: String) {
        val orderId = getOrderHistoryRef().push().key ?: ""
        val newOrder = Order(
            orderId = orderId,
            totalAmount = totalAmount,
            date = date,  // Сохраняем дату и время заказа с учетом GMT+5
            products = cartProducts
        )
        saveOrderToFirebase(newOrder)
    }

    fun increaseQuantity(barcode: String) {
        val index = _cartProducts.value.indexOfFirst { it.barcode == barcode }
        if (index != -1) {
            val product = _cartProducts.value[index]
            val updatedProduct = product.copy(quantity = product.quantity + 1)

            val updatedList = _cartProducts.value.toMutableList()
            updatedList[index] = updatedProduct
            _cartProducts.value = updatedList

            updateProductInFirebase(updatedProduct)
        }
    }

    fun decreaseQuantity(barcode: String) {
        val index = _cartProducts.value.indexOfFirst { it.barcode == barcode }
        if (index != -1) {
            val product = _cartProducts.value[index]
            if (product.quantity > 1) {
                val updatedProduct = product.copy(quantity = product.quantity - 1)

                val updatedList = _cartProducts.value.toMutableList()
                updatedList[index] = updatedProduct
                _cartProducts.value = updatedList

                updateProductInFirebase(updatedProduct)
            } else {
                removeProduct(barcode)
            }
        }
    }

    fun addProduct(product: Product) {
        val cartRef = getCartRef() // Ссылка на корзину

        // Проверяем, есть ли уже товар с таким именем в корзине
        cartRef.orderByChild("name").equalTo(product.name).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Если товар с таким именем уже есть в корзине, увеличиваем его количество
                    val productKey = snapshot.children.firstOrNull()?.key
                    productKey?.let {
                        val existingProduct = snapshot.children.firstOrNull()?.getValue(Product::class.java)
                        existingProduct?.let { existing ->
                            val updatedProduct = existing.copy(quantity = existing.quantity + 1) // Увеличиваем количество
                            cartRef.child(it).setValue(updatedProduct) // Обновляем количество в Firebase
                            Log.d("Firebase", "Updated quantity for existing product: ${updatedProduct.name}")
                        }
                    }
                } else {
                    // Если товара с таким именем нет, добавляем новый товар в корзину
                    val newProduct = product.copy(quantity = 1) // Начальное количество = 1
                    val newProductRef = cartRef.push() // Генерируем уникальный ключ для нового товара
                    newProductRef.setValue(newProduct) // Добавляем товар в Firebase
                    Log.d("Firebase", "Added new product to cart: ${newProduct.name}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error checking for existing product: ${error.message}")
            }
        })
    }



    private fun updateProductInFirebase(product: Product) {
        getCartRef().orderByChild("barcode").equalTo(product.barcode)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.firstOrNull()?.key?.let { key ->
                        getCartRef().child(key).setValue(product)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
    fun loadCartFromFirebase() {
        getCartRef().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                snapshot.children.forEach {
                    it.getValue(Product::class.java)?.let { product ->
                        products.add(product)
                    }
                }
                _cartProducts.value = products // Обновляем состояние
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error loading cart: ${error.message}")
            }
        })
    }
    fun removeProduct(barcode: String) {
        val cartRef = getCartRef()

        // Удаляем товар по баркоду
        cartRef.orderByChild("barcode").equalTo(barcode).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.firstOrNull()?.key?.let { key ->
                    // Удаляем только тот товар, который соответствует выбранному баркоду
                    cartRef.child(key).removeValue()
                    Log.d("Firebase", "Removed product with barcode: $barcode")

                    // Обновляем локальный список товаров в корзине
                    _cartProducts.value = _cartProducts.value.filter { it.barcode != barcode }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error removing product: ${error.message}")
            }
        })
    }


    fun clearCart() {
        _cartProducts.value = emptyList()
        getCartRef().removeValue()
    }




    fun loadOrderHistory() {
        getOrderHistoryRef().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orders = mutableListOf<Order>()
                snapshot.children.forEach { orderSnapshot ->
                    val order = orderSnapshot.getValue(Order::class.java)
                    order?.let { orders.add(it) }
                }
                _orderHistory.value = orders
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun createOrder(): Order {
        val orderId = getOrderHistoryRef().push().key ?: ""
        val totalAmount = _cartProducts.value.sumOf { it.price * it.quantity }
        val formattedDate = SimpleDateFormat("dd.MM.yyyy (EEEE, HH:mm)", Locale.getDefault()).format(Date()) // Форматированная дата

        val newOrder = Order(
            orderId = orderId,
            totalAmount = totalAmount,
            date = formattedDate,  // Форматированная дата
            orderTime = formattedDate,  // Время, когда пользователь нажал "Pay Now"
            products = _cartProducts.value
        )

        saveOrderToFirebase(newOrder)
        return newOrder
    }
}



fun getProductDetailsFromFirebase(barcode: String, onResult: (Product?) -> Unit) {
    val productRef = FirebaseDatabase.getInstance().getReference(barcode)
    productRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
