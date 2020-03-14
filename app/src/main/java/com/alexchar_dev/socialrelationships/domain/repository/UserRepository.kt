package com.alexchar_dev.socialrelationships.domain.repository

import androidx.lifecycle.LiveData

interface UserRepository {
    fun createUserWithEmail(email: String, password: String, username: String)
    fun observeRegistrationResponse() : LiveData<Boolean>
    suspend fun isEmailValid(email: String) : Boolean
    suspend fun isUsernameTaken(username: String) : Boolean
}