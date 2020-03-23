package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.usecase.SearchUserCase

class SearchViewModel(private val searchUseCase: SearchUserCase) : ViewModel()
{
    private var searchTerm: String? = null
}