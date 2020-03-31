package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.usecase.SignUpCase

class SignUpViewModel(private val signUpCase: SignUpCase) : ViewModel() {

    private var _registrationResponse = signUpCase.observeRegistrationResponse()
    private var _checkUsernameResponse = signUpCase.observeCheckUsernameResponse()

    val registrationResponse : LiveData<Boolean>
        get() = _registrationResponse

    val checkUsernameResponse : LiveData<Boolean>
        get() = _checkUsernameResponse

    fun createUser(email: String, password: String, username: String) {
        signUpCase.createUserWithEmail(email, password, username)
    }

    fun checkUsernameTaken(username: String) {
        signUpCase.isUsernameTaken(username)
    }

    fun clearLiveData(){
        _registrationResponse.value = null
        _checkUsernameResponse.value = null
    }
}
