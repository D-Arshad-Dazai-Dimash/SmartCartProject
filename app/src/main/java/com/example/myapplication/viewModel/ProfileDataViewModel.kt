import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ProfileDataViewModel : ViewModel() {
    var userEmail by mutableStateOf("")
    var userName by mutableStateOf("")

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().getReference("users").child(userId)

            database.get().addOnSuccessListener { snapshot ->
                userEmail = snapshot.child("email").getValue(String::class.java) ?: "Email Not Available"
                userName = snapshot.child("name").getValue(String::class.java) ?: "Name Not Available"
            }
        }
    }

    fun updateUserName(newName: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: return
        val database = FirebaseDatabase.getInstance().getReference("users").child(userId)

        database.child("name").setValue(newName).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userName = newName
            }
        }
    }
}
