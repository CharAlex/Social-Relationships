package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class EmailValidationCaseImpl(private val userRepository: UserRepository) : EmailValidationCase {
    override suspend fun isEmailValid(email: String): Boolean {
        return userRepository.isEmailValid(email)
    }


}
//TODO MOVE HERE THE LOGIC FOR THE MAIL VALIDATION
interface EmailValidationCase {
    suspend fun isEmailValid(email: String) : Boolean
}
