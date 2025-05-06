package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.BottomNavigationBar
import com.example.myapplication.ui.components.profileScreen.ProfileButton
import com.example.myapplication.viewModel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    val profileViewModel: ProfileViewModel = viewModel()

    val userName = profileViewModel.userName.value
    val userEmail = profileViewModel.userEmail.value

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Back arrow",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .size(45.dp, 44.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.height(35.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ellipse),
                        contentDescription = "Profile Avatar",
                        modifier = Modifier
                            .size(59.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = userEmail,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF363636),
                            letterSpacing = 3.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate("personalData") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                            .height(62.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFFFF),
                            contentColor = Color.Black
                        ),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.user_icon),
                                contentDescription = "",
                                modifier = Modifier.size(22.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = "Personal Data",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = userEmail,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Thin
                                    ),
                                    color = Color.Black,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(1.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileButton(
                        id = R.drawable.bell_icon,
                        text = "Notifications",
                        onClick = { navController.navigate("notifications") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileButton(
                        id = R.drawable.purchase_icon,
                        text = "My Purchases",
                        onClick = { navController.navigate("purchases") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileButton(
                        id = R.drawable.credit_card_icon,
                        text = "Payment Methods",
                        onClick = { navController.navigate("cards") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileButton(
                        id = R.drawable.exit_icon,
                        text = "Log out",
                        onClick = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }

                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        })
}
