package com.alexchar_dev.socialrelationships.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface UserRepository {
    fun createUserWithEmail(email: String, password: String, username: String)
    fun observeRegistrationResponse() : MutableLiveData<Boolean>
    suspend fun isEmailValid(email: String) : Boolean
    suspend fun isUsernameTaken(username: String) : Boolean
}