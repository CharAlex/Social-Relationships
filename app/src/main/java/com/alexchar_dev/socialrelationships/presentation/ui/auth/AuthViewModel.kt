package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    var email: String? = null
    var password: String? = null

    fun onLoginButtonClick(view: View){
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            println()
        }
    }

    fun onCreateAccountClick(view: View){
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            println("debug: should call usecase create account")
        }
    }
}