package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _userName = mutableStateOf("Name Not Available")
    private val _userEmail = mutableStateOf("Email Not Available")

    val userName = _userName
    val userEmail = _userEmail

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val user = auth.currentUser
        user?.let {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().getReference("users").child(userId)

            database.get().addOnSuccessListener { snapshot ->
                _userName.value =
                    snapshot.child("name").getValue(String::class.java) ?: "Name Not Available"
                _userEmail.value =
                    snapshot.child("email").getValue(String::class.java) ?: "Email Not Available"
            }.addOnFailureListener {
                Log.e("ProfileViewModel", "Failed to fetch user data: ${it.message}")
            }
        }
    }
}
