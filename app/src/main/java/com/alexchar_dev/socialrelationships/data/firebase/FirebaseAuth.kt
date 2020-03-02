package com.alexchar_dev.socialrelationships.data.firebase

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

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
}