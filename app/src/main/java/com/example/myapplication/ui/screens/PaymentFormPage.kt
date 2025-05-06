package com.example.myapplication.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.viewModel.CartViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
@Composable
fun PaymentFormPage(
    navController: NavController,
    amount: String,
    cartViewModel: CartViewModel
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf(" /") }
    var cvc by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val cartProducts = cartViewModel.cartProducts
    val formattedTotal = amount

    fun isValidExpiryDate(date: String): Boolean {
        return date.length == 5 && date[2] == '/' && date.substring(0, 2).all { it.isDigit() } && date.substring(3).all { it.isDigit() }
    }

    fun isValidCardNumber(number: String): Boolean {
        return number.length == 16 && number.all { it.isDigit() }
    }

    fun isValidCVC(cvcInput: String): Boolean {
        return cvcInput.length == 3 && cvcInput.all { it.isDigit() }
    }

    fun checkFormValidity() {
        isButtonEnabled = isValidCardNumber(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVC(cvc)
    }

    fun handleExpiryDateInput(input: String) {
        if (input.length <= 5) {
            val formattedInput = when {
                input.length == 2 && !input.contains("/") -> "$input/"
                input.length == 3 && input[2] != '/' -> "${input.substring(0, 2)}/" // Добавить слэш
                input.length <= 5 -> input // Даем возможность вводить только 2 цифры до слэша и 2 после
                else -> expiryDate
            }
            expiryDate = formattedInput
            checkFormValidity()
        }
    }

    fun handleExpiryDateChange(value: String) {
        if (value.length <= 5) {
            val formattedValue = when {
                value.length == 2 && !value.contains("/") -> "$value/"
                value.length == 3 && value[2] != '/' -> value.substring(0, 2) + "/" + value.substring(2) // Добавить слэш автоматически
                value.length <= 5 -> value
                else -> expiryDate
            }
            expiryDate = formattedValue
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Payment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = {},
                    label = { Text("Amount") },
                    leadingIcon = { Icon(Icons.Default.Settings, contentDescription = "Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    readOnly = true
                )

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 16) {
                            cardNumber = it
                            checkFormValidity()
                        }
                    },
                    label = { Text("Card Number") },
                    leadingIcon = { Icon(Icons.Default.Add, contentDescription = "Card") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    maxLines = 1
                )

                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { handleExpiryDateChange(it) },
                    label = { Text("Expiry Date (MM/YY)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = cvc,
                    onValueChange = {
                        if (it.length <= 3) {
                            cvc = it
                            checkFormValidity()
                        }
                    },
                    label = { Text("CVC") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword)
                )

                Button(
                    onClick = {
                        val totalAmount = convertToDouble(formattedTotal)

                        val timeZone = TimeZone.getTimeZone("GMT+5")
                        val sdf = SimpleDateFormat("dd.MM.yyyy (EEEE, HH:mm)", Locale.getDefault())
                        sdf.timeZone = timeZone
                        val currentTime = sdf.format(Date())

                        val orderId = "ORDER_${System.currentTimeMillis()}"

                        // Логирование
                        Log.d("PaymentForm", "Current time: $currentTime")
                        Log.d("PaymentForm", "Formatted total: $formattedTotal")
                        Log.d("PaymentForm", "Generated Order ID: $orderId")

                        // Проверка данных перед сохранением
                        if (formattedTotal.isEmpty() || cardNumber.isEmpty() || expiryDate.isEmpty() || cvc.isEmpty()) {
                            Log.e("PaymentForm", "Fields are empty!")
                        } else {
                            cartViewModel.saveOrder(totalAmount, cartProducts, currentTime)

                            Log.d("PaymentForm", "Order saved successfully.")

                            navController.navigate("receipt/$formattedTotal/$orderId")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7A00)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = isButtonEnabled
                ) {
                    Text("Pay Now", color = Color.White, fontSize = 18.sp)
                }

            }
        }
    }
}
fun convertToDouble(amount: String): Double {
    val formattedAmount = amount.replace(",", ".")
    return try {
        formattedAmount.toDouble()
    } catch (e: NumberFormatException) {
        Log.e("PaymentForm", "Invalid number format: $formattedAmount")
        0.0
    }
}