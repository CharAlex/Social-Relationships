package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.repository.UserRepository


class NewEmailAccountCaseImpl(private val userRepository: UserRepository) : NewEmailAccountCase {

    override fun createUserWithEmail(email: String, password: String, username: String) : Boolean {
        //create account here and if need to return something add after invoke() : something
        return userRepository.createUserWithEmail(email, password, username)
    }

    override suspend fun isUsernameTaken(username: String) : Boolean{
        return userRepository.isUsernameTaken(username)
    }
}

interface NewEmailAccountCase {
    fun createUserWithEmail(email: String, password: String, username: String) : Boolean
    suspend fun isUsernameTaken(username: String) : Boolean
}

