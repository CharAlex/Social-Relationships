package com.alexchar_dev.socialrelationships.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.data.firebase.FirebaseAuth
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override fun observeRegistrationResponse(): MutableLiveData<Boolean> {
        return firebaseAuth.getRegistrationResponse()
    }

    override fun observeCheckUsernameResponse(): MutableLiveData<Boolean> {
        return firebaseAuth.getUsernameCheckResponse()
    }

    override fun createUserWithEmail(email: String, password: String, username: String){
        firebaseAuth.createFirebaseUser(email, password, username)
    }

    override suspend fun isEmailValid(email: String): Boolean {
        return firebaseAuth.isValidEmail(email)
    }

    override fun isUsernameTaken(username: String) {
        firebaseAuth.isUsernameTaken(username)
    }
}