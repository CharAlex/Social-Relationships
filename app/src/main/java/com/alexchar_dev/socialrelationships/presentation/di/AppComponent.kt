package com.alexchar_dev.socialrelationships.presentation.di

import org.koin.core.module.Module

val appComponent: List<Module> = listOf(
    authModule,
    repositoriesModule,
    navigationModule
)