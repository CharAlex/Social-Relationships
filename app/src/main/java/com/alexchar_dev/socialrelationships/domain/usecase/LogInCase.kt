package com.alexchar_dev.socialrelationships.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository

class LogInCaseImpl(private val repository: UserRepository) : LogInCase {
    override fun logIn(email: String, password: String) : MutableLiveData<Boolean> {
        return repository.logIn(email, password)
    }

}

interface LogInCase {
    fun logIn(email: String, password: String) : MutableLiveData<Boolean>
}