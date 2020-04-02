package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import androidx.lifecycle.*
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.domain.usecase.FriendRequestCase
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class SearchViewModel(private val searchUseCase: FriendRequestCase) : ViewModel()
{

    fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        return searchUseCase.getUserSearchResult(searchTerm)
    }

    fun sendFriendRequest(userId: String?) = liveData {
        emitSource(searchUseCase.sendFriendRequest(userId))
    }

}

