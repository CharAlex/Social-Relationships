package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.domain.usecase.*
import com.alexchar_dev.socialrelationships.presentation.MainActivityViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.home.HomeViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.profile.ProfileViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests.FriendRequestFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests.FriendRequestViewModel
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val navigationModule = module {
    single<FriendRequestCase> {
        FriendRequestCaseImpl(get())
    }
    single<FriendshipCase> { FriendshipCaseImpl(get()) }

    single<SignOutCase> { SignOutCaseImpl( get() ) }

    viewModel { SearchViewModel( get() ) }
    viewModel { FriendRequestViewModel( get() ) }
    viewModel { ProfileViewModel( get()) }
    viewModel { MainActivityViewModel() }
    viewModel { HomeViewModel(get()) }
}