package com.alexchar_dev.socialrelationships.data

import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.data.firebase.firebaseAuth.FirebaseAuthService
import com.alexchar_dev.socialrelationships.domain.entity.Result
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class UserRepositoryImpl(
    private val firebaseAuthService: FirebaseAuthService
) : UserRepository {
    override fun observeRegistrationResponse(): MutableLiveData<Boolean> {
        return firebaseAuthService.getRegistrationResponse()
    }

    override fun observeCheckUsernameResponse(): MutableLiveData<Boolean> {
        return firebaseAuthService.getUsernameCheckResponse()
    }

    override fun createUserWithEmail(email: String, password: String, username: String){
        firebaseAuthService.createFirebaseUser(email, password, username)
    }

    override suspend fun isEmailValid(email: String): Result<Boolean> {
        return firebaseAuthService.isEmailValid(email)
    }

    override fun isUsernameTaken(username: String) {
        firebaseAuthService.isUsernameTaken(username)
    }
}