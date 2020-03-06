package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.usecase.EmailValidationCase
class AuthHomeViewModel(
    private val EmailValidationCase: EmailValidationCase
) : ViewModel() {
    //val email: String? = null

    suspend fun isEmailValid(email: String): Boolean {
        return EmailValidationCase.isEmailValid(email)
    }
}