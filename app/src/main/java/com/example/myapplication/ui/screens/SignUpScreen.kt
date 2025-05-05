package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.PutBackGroundImage
import com.example.myapplication.ui.components.SignUpScreen.CustomTextField
import com.example.myapplication.ui.components.SignUpScreen.PasswordStrengthIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordStrength by remember { mutableStateOf(0f) }
    var passwordStrengthText by remember { mutableStateOf("At least 8 characters") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    var isChecked by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()

    fun validate() {
        passwordStrength = when {
            password.length in 1..5 -> 0.25f
            password.length in 6..8 -> 0.5f
            password.length >= 9 -> 1f
            else -> 0f
        }

        passwordStrengthText = when {
            password.length in 1..5 -> "Weak"
            password.length in 6..8 -> "Medium"
            password.length >= 9 -> "Strong"
            else -> "At least 8 characters"
        }

        passwordError =
            if (password.matches(Regex("^(?=.*[A-Z].*[A-Z])(?=.*[!@#\$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}\$"))) "At least 8 characters" else ""
        confirmPasswordError = if (confirmPassword != password) "Passwords do not match" else ""

        isChecked =
            passwordError.isEmpty() && confirmPasswordError.isEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
    }

    fun registerUser() {
        validate()

        if (passwordError.isEmpty() && confirmPasswordError.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val userId = user?.uid ?: return@addOnCompleteListener

                        user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                Toast.makeText(
                                    navController.context,
                                    "Verification email sent. Please check your inbox.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val userDatabaseRef =
                                    FirebaseDatabase.getInstance().getReference("users")
                                val userData = mapOf("name" to name, "email" to email)

                                userDatabaseRef.child(userId).setValue(userData)
                                    .addOnCompleteListener { dbTask ->
                                        if (dbTask.isSuccessful) {
                                            Toast.makeText(
                                                navController.context,
                                                "Registration Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.navigate("login")
                                        } else {
                                            Toast.makeText(
                                                navController.context,
                                                "Database error: ${dbTask.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    "Failed to send verification email: ${verificationTask.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Registration failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .systemBarsPadding()
            .consumeWindowInsets(WindowInsets.systemBars)
    ) {
        PutBackGroundImage(R.drawable.signup)

        Column(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "Sign Up",
                        style = TextStyle(fontSize = 42.sp),
                        fontWeight = FontWeight.W700,
                        color = Color(0xFF363636)
                    )
                    Text(
                        "Please enter your details",
                        style = TextStyle(fontSize = 16.sp),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 90.dp)
                ) {

                    Button(
                        onClick = { },
                        modifier = Modifier.size(354.dp, 52.dp),
                        border = BorderStroke(1.dp, Color(0xFFD1CFCF)),
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Black,
                            disabledContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google_button),
                            contentDescription = "google icon",
                            modifier = Modifier
                                .size(15.dp)
                        )
                        Text(
                            "Sign up with Google",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 18.dp))

                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Full Name",
                        placeholder = "Enter full name"
                    )

                    Spacer(modifier = Modifier.padding(top = 10.dp))


                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "Enter email",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            validate()
                        },
                        label = "Password",
                        placeholder = "At least 8 characters",
                        visualTransformation = PasswordVisualTransformation(),
                        error = passwordError
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            validate()
                        },
                        label = "Confirm Password",
                        placeholder = "At least 8 characters",
                        visualTransformation = PasswordVisualTransformation(),
                        error = confirmPasswordError
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordStrengthIndicator(
                        strength = passwordStrength,
                        strengthText = passwordStrengthText
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFFCDCCD),
                                        Color(0xFFCE5F27)
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                    ) {
                        Button(
                            onClick = { registerUser() },
                            enabled = isChecked,
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text(
                                text = "Sign Up",
                                color = Color.White,
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Text(
                        "Already have an account?",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    )

                    Spacer(modifier = Modifier.padding(top = 20.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("login")
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(1.dp, Color(0xFFF08F5F))
                        ) {
                            Text(
                                text = "Log in",
                                color = Color(0xFFF08F5F),
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                            )
                        }
                    }
                }
            }
        }
    }
}
//Assylzhan