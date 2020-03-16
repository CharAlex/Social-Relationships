package com.alexchar_dev.socialrelationships.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirebaseAuth {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var registrationResponse =  MutableLiveData<Boolean>()
    private var usernameCheckResponse = MutableLiveData<Boolean>()

    companion object {
        var count = 0
    }

    fun createFirebaseUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        println("debug: reg" +
                                "istration successful: $user")
                        createFirestoreUserCollection(email, password, username)
                    }
                } else {
                    println("debug: registration failed")
                    registrationResponse.postValue(false)
                }
            }
    }

    fun getRegistrationResponse() = registrationResponse
    fun getUsernameCheckResponse() = usernameCheckResponse

    private fun createFirestoreUserCollection(userId: String, email: String, username: String) {
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
                    registrationResponse.postValue(true)
                } else {
                    println("debug: user collection creation failed")
                    registrationResponse.postValue(false)
                }
            }
    }


    suspend fun isValidEmail(email: String): Boolean {
        val user = auth.fetchSignInMethodsForEmail(email).await()
        return user.signInMethods?.isEmpty() ?: false
    }

    fun isUsernameTaken(username: String) {

        println("debug: Firebase read operation ${count++}")

        db.collection("users")
            .whereEqualTo("username", username)
            .limit(1)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in task.result!!) {
                        println("debug: documentSnapshot: $documentSnapshot")
                        val user = documentSnapshot.getString("username")
                        if (user == username) {
                            usernameCheckResponse.postValue(false)
                            return@addOnCompleteListener
                        }
                    }
                    usernameCheckResponse.postValue(true)
                }
            }
    }
}
