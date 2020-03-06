package com.alexchar_dev.socialrelationships.data

import com.alexchar_dev.socialrelationships.data.firebase.FirebaseAuth
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override fun createUserWithEmail(email: String, password: String) {
        firebaseAuth.createFirebaseUser(email, password)
    }

    override suspend fun isValidEmail(email: String) : Boolean{
        return firebaseAuth.isValidEmail(email)
    }
}