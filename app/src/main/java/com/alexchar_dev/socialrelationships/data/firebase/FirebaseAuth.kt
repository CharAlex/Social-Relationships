package com.alexchar_dev.socialrelationships.data.firebase

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuth {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createFirebaseUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    println("debug: registration succesfull: $user")
                } else {
                    println("debug: registration failed")
                }
            }
    }

    suspend fun isValidEmail(email: String): Boolean {
        val user = auth.fetchSignInMethodsForEmail(email).await()
        return user.signInMethods?.isEmpty() ?: false
    }
}
