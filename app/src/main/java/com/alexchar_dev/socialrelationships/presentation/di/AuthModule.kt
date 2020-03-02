package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.domain.usecase.NewEmailAccountCase
import com.alexchar_dev.socialrelationships.domain.usecase.NewEmailAccountCaseImpl
import com.alexchar_dev.socialrelationships.presentation.ui.auth.NewEmailAccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<NewEmailAccountCase> {
        NewEmailAccountCaseImpl(
            get()
        )
    }
    viewModel { NewEmailAccountViewModel(get()) }
}