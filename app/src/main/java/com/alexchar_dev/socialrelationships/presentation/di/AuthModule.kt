package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.domain.usecase.SignUpCase
import com.alexchar_dev.socialrelationships.domain.usecase.SignUpCaseImpl
import com.alexchar_dev.socialrelationships.presentation.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { AuthViewModel() }
}