package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.domain.usecase.FriendRequestCase
import com.alexchar_dev.socialrelationships.domain.usecase.FriendRequestCaseImpl
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests.FriendRequestFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests.FriendRequestViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val navigationModule = module {
    single<FriendRequestCase> {
        FriendRequestCaseImpl(get())
    }

    viewModel { SearchViewModel( get() ) }
    viewModel { FriendRequestViewModel( get() ) }
}