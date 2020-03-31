package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.domain.usecase.*
import com.alexchar_dev.socialrelationships.presentation.ui.auth.AuthHomeViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.auth.LogInViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.auth.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<SignUpCase> { SignUpCaseImpl(get()) }
    single<EmailValidationCase> { EmailValidationCaseImpl(get()) }
    single<LogInCase> { LogInCaseImpl(get())}

    viewModel { SignUpViewModel(get()) }
    viewModel { LogInViewModel(get()) }
    viewModel { AuthHomeViewModel(get()) }
}