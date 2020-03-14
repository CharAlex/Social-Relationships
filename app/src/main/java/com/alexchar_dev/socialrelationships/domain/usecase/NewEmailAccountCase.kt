package com.alexchar_dev.socialrelationships.domain.usecase

import androidx.lifecycle.LiveData
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository


class NewEmailAccountCaseImpl(private val userRepository: UserRepository) : NewEmailAccountCase {

    override fun createUserWithEmail(email: String, password: String, username: String) {
        userRepository.createUserWithEmail(email, password, username)
    }

    override fun observeRegistrationResponse(): LiveData<Boolean> {
        return userRepository.observeRegistrationResponse()
    }

    override suspend fun isUsernameTaken(username: String) : Boolean{
        return userRepository.isUsernameTaken(username)
    }
}

interface NewEmailAccountCase {
    fun createUserWithEmail(email: String, password: String, username: String)
    fun observeRegistrationResponse() : LiveData<Boolean>
    suspend fun isUsernameTaken(username: String) : Boolean
}

