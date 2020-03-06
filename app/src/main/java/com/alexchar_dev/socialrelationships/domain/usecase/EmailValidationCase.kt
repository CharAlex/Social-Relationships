package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class EmailValidationCaseImpl(private val userRepository: UserRepository) : EmailValidationCase {
    override suspend fun isEmailValid(email: String): Boolean {
        return userRepository.isValidEmail(email)
    }


}

interface EmailValidationCase {
    suspend fun isEmailValid(email: String) : Boolean
}
