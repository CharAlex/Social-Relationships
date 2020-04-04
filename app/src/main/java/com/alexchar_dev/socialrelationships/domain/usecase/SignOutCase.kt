package com.alexchar_dev.socialrelationships.domain.usecase

import androidx.lifecycle.LiveData
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class SignOutCaseImpl(private val repository: UserRepository) : SignOutCase {
    override fun signOut(): LiveData<Boolean> {
        return repository.signOut()
    }

    override fun clearPersistence() {
        repository.clearPersistence()
    }
}

interface SignOutCase {
    fun signOut() : LiveData<Boolean>
    fun clearPersistence()
}