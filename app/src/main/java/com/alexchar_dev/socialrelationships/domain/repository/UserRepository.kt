package com.alexchar_dev.socialrelationships.domain.repository

interface UserRepository {
    fun createUserWithEmail(email: String, password: String, username: String) : Boolean
    suspend fun isEmailValid(email: String) : Boolean
    suspend fun isUsernameTaken(username: String) : Boolean
}