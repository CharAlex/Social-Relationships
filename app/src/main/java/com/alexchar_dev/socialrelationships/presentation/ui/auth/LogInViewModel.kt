package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.usecase.LogInCase

class LogInViewModel(private val logInCase: LogInCase) : ViewModel() {

    fun logIn(email: String, password: String) : MutableLiveData<Boolean> {
        return logInCase.logIn(email, password)
    }
}
