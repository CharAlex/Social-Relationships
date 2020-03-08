package com.alexchar_dev.socialrelationships.data.firebase

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.koin.ext.getScopeId

class FirebaseAuth {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createFirebaseUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if(user != null){
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                createFirestoreUserCollection(user.uid, email)
                                println("debug: registration successful: $user")
                                //TODO if registration successful start new activity
                            }
                        }
                    }
                } else {
                    println("debug: registration failed")
                }
            }
    }

    private suspend fun createFirestoreUserCollection(userId: String, email: String) {
        //TODO store username
        val user = hashMapOf(
            "uid" to userId,
            "username" to "test",
            "email" to email,
            "isVerified" to false
        )

        db.collection("users").document(userId).set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("debug: collection created successfully: ${task}")
                } else {
                    println("debug: user collection creation failed")
                }
            }.await()
    }

    suspend fun isValidEmail(email: String): Boolean {
        val user = auth.fetchSignInMethodsForEmail(email).await()
        return user.signInMethods?.isEmpty() ?: false
    }
}
