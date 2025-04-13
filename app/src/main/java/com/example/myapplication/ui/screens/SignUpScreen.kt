package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.PutBackGroundImage

@Preview(showBackground = true)
@Composable
fun SignUpScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Box(){
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .systemBarsPadding()
                .consumeWindowInsets(WindowInsets.systemBars)
        ) {
            PutBackGroundImage(R.drawable.signup)
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)){
            Column(modifier = Modifier.
            padding(top = 95.dp).
            fillMaxWidth()) {
                Text("Sign Up" , style = TextStyle(fontSize = 42.sp), fontWeight = FontWeight.W700 , color = Color(0xFF363636))
                Text("Please enter your details" , style = TextStyle(fontSize = 16.sp) , fontWeight = FontWeight.SemiBold )
            }
            Column(modifier = Modifier.
            fillMaxSize().
            padding(bottom = 50.dp)) {

            }
        }

    }
}