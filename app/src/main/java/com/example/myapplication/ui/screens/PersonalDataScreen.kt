import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileDataScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val profileDataViewModel: ProfileDataViewModel = viewModel()

    var isEditingName by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(profileDataViewModel.userName) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    fun handleUpdateName() {
        profileDataViewModel.updateUserName(newName)
    }

    fun handlePasswordReset() {
        if (newPassword != confirmPassword) {
            passwordError = "Passwords do not match!"
            return
        }

        user?.let {
            it.updatePassword(newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Password updated successfully")
                } else {
                    passwordError = "Failed to update password"
                }
            }
        }
    }

    fun sendPasswordResetEmail() {
        user?.email?.let {
            auth.sendPasswordResetEmail(it)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Password reset email sent.")
                    } else {
                        println("Failed to send password reset email.")
                    }
                }
        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.65f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Back arrow",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(45.dp, 44.dp)
                            .clickable {
                                navController.popBackStack("profile", inclusive = false)
                            }
                    )
                    Text(
                        text = "Personal Data",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(top = 50.dp)
                        .padding(paddingValues)
//                        .background(Color(0xFFFFFFFF))
                ) {


                    Text(
                        text = "Email: ${profileDataViewModel.userEmail}",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = "Name: ${profileDataViewModel.userName}",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    if (isEditingName) {
                        BasicTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            textStyle = TextStyle(fontSize = 18.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(Color(0xFFEEEBEB), shape = RoundedCornerShape(10.dp))
                                .padding(16.dp),
                            maxLines = 1,
                            singleLine = true
                        )
                        Button(
                            onClick = {
                                handleUpdateName()
                                isEditingName = false
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color(0xffF08F5F)
                            ),
                        ) {
                            Text("Save")
                        }
                    } else {
                        Button(
                            onClick = {
                                isEditingName = true
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color(0xffF08F5F)
                            ),
                        ) {
                            Text("Edit Name")
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(text = "Change Password", fontSize = 22.sp, fontWeight = FontWeight.Bold)

                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Current Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { handlePasswordReset() },
                        modifier = Modifier.fillMaxWidth(), colors = ButtonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xffF08F5F),
                            disabledContainerColor = Color(0xffF08F5F),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text("Change Password")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Forgot Password? Reset via Email",
                        color = Color.Blue,
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
                        modifier = Modifier.clickable { sendPasswordResetEmail() }.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                }
            }
        }
    )
}

