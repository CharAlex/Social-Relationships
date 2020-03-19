package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.entity.Result
import com.alexchar_dev.socialrelationships.domain.usecase.EmailValidationCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthHomeViewModel(
    private val EmailValidationCase: EmailValidationCase
) : ViewModel() {
    //val email: String? = null

    suspend fun isEmailValid(email: String): Result<Boolean> {
        withContext(Dispatchers.IO){

        }
        return EmailValidationCase.isEmailValid(email)
    }
}