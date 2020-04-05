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

    fun sendFriendRequest(userId: String?) = liveData {//TODO liveData<Result>
        val user = searchUseCase.getCurrentUser()
        if (user!!.checkFriendStatus(userId)) { //if the user already got a friend request then accept it instead of sending friend request to the other user
            val request = searchUseCase.getFriendRequest(user.userId, userId!!)
            emitSource(searchUseCase.acceptFriendRequest(request!!))
        } else {
            emitSource(searchUseCase.sendFriendRequest(userId))
        }
    }

    private fun User.checkFriendStatus(uid: String?) = this.requestIds.contains(uid)

}

