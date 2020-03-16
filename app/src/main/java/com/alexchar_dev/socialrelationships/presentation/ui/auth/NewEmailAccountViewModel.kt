package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchar_dev.socialrelationships.domain.usecase.NewEmailAccountCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewEmailAccountViewModel(
    private val newEmailAccountCase: NewEmailAccountCase
) : ViewModel() {

    private var _registrationResponse = newEmailAccountCase.observeRegistrationResponse()
    private var _checkUsernameResponse = newEmailAccountCase.observeCheckUsernameResponse()

    val registrationResponse : LiveData<Boolean>
        get() = _registrationResponse

    val checkUsernameResponse : LiveData<Boolean>
        get() = _checkUsernameResponse

    fun createUser(email: String, password: String, username: String) {
        newEmailAccountCase.createUserWithEmail(email, password, username)
    }

    fun checkUsernameTaken(username: String) {
        newEmailAccountCase.isUsernameTaken(username)
    }

    fun clearLiveData(){
        _registrationResponse.value = null
        _checkUsernameResponse.value = null
    }
}
