package com.alexchar_dev.socialrelationships.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseAuth {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    companion object {
        var count = 0
    }

    fun createFirebaseUser(email: String, password: String, username: String) : Boolean {
        var accountCreated = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if(user != null){
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                createFirestoreUserCollection(user.uid, email, username)
                                println("debug: registration successful: $user")
                                //TODO if registration successful start new activity
                                accountCreated = true
                            }
                        }
                    }
                } else {
                    println("debug: registration failed")
                    accountCreated = false
                }
            }
        return accountCreated
    }

    private suspend fun createFirestoreUserCollection(userId: String, email: String, username: String) {
        val user = hashMapOf(
            "uid" to userId,
            "username" to username,
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

    suspend fun isUsernameTaken(username: String): Boolean {

        println("debug: Firebase read operation ${count++}")

        var isTaken = false
        db.collection("users")
            .whereEqualTo("username", username)
            .limit(1)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    for (documentSnapshot in task.result!!) {
                        println("debug: documentSnapshot: $documentSnapshot")
                        val user = documentSnapshot.getString("username")
                        if (user == username) {
                            isTaken = true
                        }
                    }
                    println("debug: isTaken in firebase: $isTaken ${task.result}")
                }
            }.await()
        return isTaken
    }
}
