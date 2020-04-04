package com.alexchar_dev.socialrelationships.presentation.ui.navigation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alexchar_dev.socialrelationships.domain.usecase.SignOutCase

class ProfileViewModel(private val signOutCase: SignOutCase) : ViewModel()
{

    fun signOut() = liveData<Boolean> {
        emitSource(signOutCase.signOut())
    }

    fun clearPersistence() {
        signOutCase.clearPersistence()
    }
}