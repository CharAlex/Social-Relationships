package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.domain.usecase.SearchUserCase
import com.alexchar_dev.socialrelationships.domain.usecase.SearchUserCaseImpl
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val navigationModule = module {
    single<SearchUserCase> {
        SearchUserCaseImpl(get())
    }

    viewModel { SearchViewModel( get() ) }
}