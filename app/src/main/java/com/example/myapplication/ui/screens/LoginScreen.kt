package com.example.myapplication.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
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

//@Preview(showBackground = true)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isPasswordCorrect by remember { mutableStateOf(true) }

    val correctPassword = "002016Zhh+"

    fun validatePassword() {
        if (password != correctPassword) {
            isPasswordCorrect = false
            passwordError = "Incorrect password"
        } else {
            isPasswordCorrect = true
            passwordError = ""
        }
    }

    Box(
        modifier = Modifier
            .background(Color.Gray)
            .systemBarsPadding()
            .consumeWindowInsets(WindowInsets.systemBars)
    ) {
        PutBackGroundImage(R.drawable.login)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 110.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Log in",
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
                    .padding(top = 150.dp)
            ) {
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "Enter email",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(22.dp))

                CustomTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        validatePassword()
                    },
                    label = "Password",
                    placeholder = "At least 8 characters",
                    visualTransformation = PasswordVisualTransformation(),
                    error = passwordError
                )


                Spacer(modifier = Modifier.height(37.dp))

                val strength = 1f

                LinearProgressIndicator(
                    progress = strength,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray),
                    color = when {
                        strength <= 0.33f -> Color.Red
                        strength in 0.34f..0.66f -> Color.Yellow
                        else -> Color(0xFFD1CFCF)
                    },
                )

                Spacer(modifier = Modifier.height(27.33.dp))

                // Log in Button (validates password)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
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
                        onClick = {
                            validatePassword()
                            if (isPasswordCorrect) {
                                navController.navigate("home")
                            }
                        },
                        enabled = email.isNotEmpty() && password.isNotEmpty(),
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "Log in",
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(115.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.size(354.dp, 52.dp),
                    border = BorderStroke(1.dp, Color(0xFFD1CFCF)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_button),
                        contentDescription = "google icon",
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        "Log in with Google",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(bottom = 27.dp))

                Text(
                    "Don't have an account? ",
                    color = Color(0xFF939090),
                    style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(19.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.White,
                                    Color.White
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    Button(
                        onClick = {
                            navController.navigate("signup")
                        },
                        modifier = Modifier
                            .fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, Color(0xFFF08F5F))
                    ) {
                        Text(
                            text = "Sign Up",
                            color = Color(0xFFF08F5F),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                        )
                    }
                }
            }
        }
    }
}