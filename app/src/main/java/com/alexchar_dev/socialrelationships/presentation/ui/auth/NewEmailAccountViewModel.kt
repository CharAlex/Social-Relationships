package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.usecase.NewEmailAccountCase

class NewEmailAccountViewModel(
    private val newEmailAccountCase: NewEmailAccountCase
) : ViewModel() {

    //call use case to create account
    fun createUser(email: String, password: String, username: String) : Boolean{
            return newEmailAccountCase.createUserWithEmail(email, password, username)
    }

    suspend fun isUsernameTaken(username: String): Boolean {
        return newEmailAccountCase.isUsernameTaken(username)
    }
}
