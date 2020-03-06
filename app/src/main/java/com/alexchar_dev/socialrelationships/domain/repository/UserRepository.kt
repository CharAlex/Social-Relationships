package com.alexchar_dev.socialrelationships.domain.repository

interface UserRepository {
    fun createUserWithEmail(email: String, password: String)
    suspend fun isValidEmail(email: String) : Boolean
}