package com.alexchar_dev.socialrelationships.data.firebase

import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.data.awaitTaskResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.alexchar_dev.socialrelationships.domain.entity.Result


class FirebaseAuthService {
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
                        createFirestoreUserCollection(email, password, username)
                    }
                } else {
                    println("debug: registration failed")
                    registrationResponse.postValue(false)
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        println("debug: FirebaseAuthWeakPasswordException")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        println("debug: FirebaseAuthInvalidCredentialsException")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        println("debug: FirebaseAuthUserCollisionException")
                    } catch (e: Exception) {
                        println("debug: Unkown exception")
                    }
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
        ) //TODO SAVE AS USER OBJECT

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


    suspend fun isEmailValid(email: String): Result<Boolean> = try {

        val task = awaitTaskResult(auth.fetchSignInMethodsForEmail(email))

        if(task.signInMethods?.isEmpty() == true)
            Result.Value(true)
        else
            Result.Value(false)
    } catch (exception: Exception) {
        Result.Error(exception)
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
