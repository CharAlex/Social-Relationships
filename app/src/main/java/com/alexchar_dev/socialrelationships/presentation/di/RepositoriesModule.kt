package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.data.UserRepositoryImpl
import com.alexchar_dev.socialrelationships.data.firebase.FirebaseAuth
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<FirebaseAuth>{ FirebaseAuth() }
    single<UserRepository> { UserRepositoryImpl(get()) }
}
