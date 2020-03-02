package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.repository.UserRepository


class NewEmailAccountCaseImpl(private val userRepository: UserRepository) : NewEmailAccountCase {

    override fun invoke(email: String, password: String) {
        //create account here and if need to return something add after invoke() : something
        userRepository.createUserWithEmail(email, password)
    }
}

interface NewEmailAccountCase {
    operator fun invoke(email: String, password: String)
}
