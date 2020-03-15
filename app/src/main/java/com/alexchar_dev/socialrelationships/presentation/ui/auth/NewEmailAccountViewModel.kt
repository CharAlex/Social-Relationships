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

    private var _registrationResponse = observeRegistrationResponse()

    val registrationResponse : LiveData<Boolean>
        get() = _registrationResponse

    //call use case to create account
    fun createUser(email: String, password: String, username: String) {
        newEmailAccountCase.createUserWithEmail(email, password, username)
    }

    private fun observeRegistrationResponse(): MutableLiveData<Boolean> {
        return newEmailAccountCase.observeRegistrationResponse()
    }

    suspend fun isUsernameTaken(username: String): Boolean {
        return newEmailAccountCase.isUsernameTaken(username)
    }

    fun clearLiveData(){
        _registrationResponse.value = null
    }
}
