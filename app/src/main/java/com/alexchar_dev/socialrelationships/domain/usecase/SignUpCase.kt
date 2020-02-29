package com.alexchar_dev.socialrelationships.domain.usecase

class SignUpCaseImpl(val temp: String) : SignUpCase {

    override suspend fun invoke() {
        println("sign up case $temp")
    }
}

interface SignUpCase {
    suspend operator fun invoke()
}