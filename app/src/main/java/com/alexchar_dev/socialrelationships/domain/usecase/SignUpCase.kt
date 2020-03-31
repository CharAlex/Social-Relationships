package com.alexchar_dev.socialrelationships.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository


class SignUpCaseImpl(private val userRepository: UserRepository) : SignUpCase {

    override fun createUserWithEmail(email: String, password: String, username: String) {
        userRepository.createUserWithEmail(email, password, username)
    }

    override fun observeRegistrationResponse(): MutableLiveData<Boolean> {
        return userRepository.observeRegistrationResponse()
    }

    override fun observeCheckUsernameResponse(): MutableLiveData<Boolean> {
        return userRepository.observeCheckUsernameResponse()
    }

    override  fun isUsernameTaken(username: String) {
        userRepository.isUsernameTaken(username)
    }
}

interface SignUpCase {
    fun createUserWithEmail(email: String, password: String, username: String)
    fun observeRegistrationResponse() : MutableLiveData<Boolean>
    fun observeCheckUsernameResponse() : MutableLiveData<Boolean>
    fun isUsernameTaken(username: String)
}

