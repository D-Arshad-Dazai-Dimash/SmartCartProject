package com.example.myapplication.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.PutBackGroundImage

//@Preview(showBackground = true)
@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isPasswordFieldTouched by remember { mutableStateOf(false) }
    var isConfirmPasswordFieldTouched by remember { mutableStateOf(false) }

    var passwordStrength by remember { mutableStateOf(0f) }
    var passwordStrengthText by remember { mutableStateOf("At least 8 characters") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    var isChecked by remember { mutableStateOf(false) }

    fun validate() {
        // Password strength calculation
        passwordStrength = when {
            password.length in 1..5 -> 0.25f // Weak password
            password.length in 6..8 -> 0.5f  // Medium strength
            password.length >= 9 -> 1f       // Strong password
            else -> 0f
        }

        passwordStrengthText = when {
            password.length in 1..5 -> "Weak"
            password.length in 6..8 -> "Medium"
            password.length >= 9 -> "Strong"
            else -> "At least 8 characters"
        }

        passwordError =
            if (password.matches(regex = Regex("^(?=.*[A-Z].*[A-Z])(?=.*[!@#\$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}\$"))) "At least 8 characters" else ""
        confirmPasswordError = if (confirmPassword != password) "Passwords do not match" else ""
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
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 95.dp)
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
                        .padding(top = 65.dp)
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
                    Spacer(modifier = Modifier.padding(bottom = 30.dp))

                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Full Name",
                        placeholder = "Enter full name"
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "Enter email",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    CustomTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            isPasswordFieldTouched =
                                true // Mark that user started typing in password field
                            validate()
                        },
                        label = "Password",
                        placeholder = "At least 8 characters",
                        visualTransformation = PasswordVisualTransformation(),
                        error = passwordError
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    CustomTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            isConfirmPasswordFieldTouched =
                                true // Mark that user started typing in confirm password field
                            validate()
                        },
                        label = "Confirm Password",
                        placeholder = "At least 8 characters",
                        visualTransformation = PasswordVisualTransformation(),
                        error = confirmPasswordError
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    PasswordStrengthIndicator(
                        strength = passwordStrength,
                        strengthText = passwordStrengthText
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            colors = CheckboxColors(
                                checkedBorderColor = Color(0xFFD1CFCF),
                                disabledBorderColor = Color(0xFFD1CFCF),
                                checkedBoxColor = Color.White,
                                checkedCheckmarkColor = Color.Black,
                                uncheckedBoxColor = Color.White,
                                uncheckedCheckmarkColor = Color.Black,
                                uncheckedBorderColor = Color(0xFFD1CFCF),
                                disabledCheckedBoxColor = Color.White,
                                disabledUncheckedBoxColor = Color.White,
                                disabledUncheckedBorderColor = Color(0xFFD1CFCF),
                                disabledIndeterminateBorderColor = Color(0xFFD1CFCF),
                                disabledIndeterminateBoxColor = Color(0xFFD1CFCF)
                            )
                        )
                        Text(
                            text = "I agree with ",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = "Terms",
                            color = Color.Blue,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.clickable { /* Add link action here */ }
                        )
                        Text(
                            text = " and ",
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = "Privacy",
                            color = Color.Blue,
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.clickable { /* Add link action here */ }
                        )
                    }
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
                                shape = RoundedCornerShape(23.dp)
                            )
                    ) {
                        Button(
                            onClick = {
                                if (passwordError.isEmpty() && confirmPasswordError.isEmpty()) {
                                    navController.navigate("home")
                                }
                            },
                            enabled = isChecked,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "Sign Up",
                                color = Color.White,
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Text(
                        "Already have an account?",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    )

                    Button(
                        onClick = {
                            navController.navigate("home")
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 15.dp, bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(23.dp),
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

@Composable
fun PasswordStrengthIndicator(strength: Float, strengthText: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
    ) {
        LinearProgressIndicator(
            progress = strength,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray),
            color = when {
                strength <= 0.33f -> Color.Red
                strength in 0.34f..0.66f -> Color.Yellow
                else -> Color.Green
            },
        )

        Text(
            text = strengthText,
            color = when {
                strength <= 0.33f -> Color.Red
                strength in 0.34f..0.66f -> Color.Yellow
                else -> Color.Green
            },
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    error: String? = null,
    isError: Boolean = false
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            visualTransformation = visualTransformation,
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
        )

        if (isError && error != null) {
            Text(text = error, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
