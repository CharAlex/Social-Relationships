package com.alexchar_dev.socialrelationships.presentation.ui.auth

import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.usecase.NewEmailAccountCase

class NewEmailAccountViewModel(
    private val newEmailAccountCase: NewEmailAccountCase
) : ViewModel() {

    //call use case to create account
    fun createUser(email: String, password: String){
            newEmailAccountCase(email, password)
    }
}
