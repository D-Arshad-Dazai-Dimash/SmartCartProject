package com.example.myapplication.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _userName = mutableStateOf("")
    private val _userEmail = mutableStateOf("")

    val userName = _userName
    val userEmail = _userEmail

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = auth.currentUser
        if (user != null) {
            _userName.value = user.displayName ?: "User Name Not Available"
            _userEmail.value = user.email ?: "Email Not Available"
        }
    }
}
