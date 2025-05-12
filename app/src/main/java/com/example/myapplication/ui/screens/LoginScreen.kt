package com.example.myapplication.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isPasswordCorrect by remember { mutableStateOf(true) }

    val auth = FirebaseAuth.getInstance()

    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("YOUR_SERVER_CLIENT_ID.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, googleSignInOptions)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
            account?.let {
                val credential = GoogleAuthProvider.getCredential(it.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            user?.let {
                                if (it.isEmailVerified) {
                                    navController.navigate("home") {
                                        popUpTo("welcome") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = "Please verify your email before logging in."
                                    Toast.makeText(
                                        navController.context,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            errorMessage = task.exception?.message ?: "Google Sign-In failed"
                            Toast.makeText(navController.context, errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    fun validatePassword() {
        if (password.isEmpty()) {
            isPasswordCorrect = false
            passwordError = "Password cannot be empty"
        } else {
            isPasswordCorrect = true
            passwordError = ""
        }
    }

    fun loginUser() {
        validatePassword()

        if (isPasswordCorrect) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.reload()?.addOnCompleteListener { reloadTask ->
                            if (reloadTask.isSuccessful) {
                                if (user.isEmailVerified) {
                                    navController.navigate("home") {
                                        popUpTo("welcome") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = "Please verify your email address."
                                    Toast.makeText(
                                        navController.context,
                                        errorMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                errorMessage =
                                    "Failed to reload user data: ${reloadTask.exception?.message}"
                                Toast.makeText(
                                    navController.context,
                                    errorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        errorMessage = task.exception?.message ?: "Login failed"
                        Toast.makeText(navController.context, errorMessage, Toast.LENGTH_SHORT)
                            .show()
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
        PutBackGroundImage(R.drawable.login)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 80.dp)
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
                        onClick = { loginUser() },
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

                Spacer(modifier = Modifier.height(27.dp))

                Button(
                    onClick = {
                        val signInIntent = googleSignInClient.signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    },
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

                Spacer(modifier = Modifier.padding(bottom = 85.dp))

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
                            navController.navigate("signup") {
                                popUpTo("welcome") { inclusive = true }
                            }
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
