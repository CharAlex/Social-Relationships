package com.alexchar_dev.socialrelationships.data

import androidx.lifecycle.LiveData
import com.alexchar_dev.socialrelationships.data.firebase.FirebaseAuth
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override fun observeRegistrationResponse(): LiveData<Boolean> {
        return firebaseAuth.getRegistrationResponse()
    }

    override fun createUserWithEmail(email: String, password: String, username: String){
        firebaseAuth.createFirebaseUser(email, password, username)
    }

    override suspend fun isEmailValid(email: String): Boolean {
        return firebaseAuth.isValidEmail(email)
    }

    override suspend fun isUsernameTaken(username: String) : Boolean{
        return firebaseAuth.isUsernameTaken(username)
    }
}