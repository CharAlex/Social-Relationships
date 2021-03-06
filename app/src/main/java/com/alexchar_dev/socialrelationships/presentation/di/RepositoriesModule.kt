package com.alexchar_dev.socialrelationships.presentation.di

import com.alexchar_dev.socialrelationships.data.UserRepositoryImpl
import com.alexchar_dev.socialrelationships.data.firebase.FirestoreService
import com.alexchar_dev.socialrelationships.data.firebase.firebaseAuth.FirebaseAuthService
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<FirebaseAuthService>{ FirebaseAuthService() }
    single{ FirestoreService() }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}
