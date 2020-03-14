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

    //call use case to create account
    fun createUser(email: String, password: String, username: String) {
        newEmailAccountCase.createUserWithEmail(email, password, username)
    }

    fun observeRegistrationResponse(): LiveData<Boolean> {
        return newEmailAccountCase.observeRegistrationResponse()
    }

    suspend fun isUsernameTaken(username: String): Boolean {
        return newEmailAccountCase.isUsernameTaken(username)
    }
}
