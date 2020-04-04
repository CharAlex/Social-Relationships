package com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alexchar_dev.socialrelationships.domain.entity.FriendRequest
import com.alexchar_dev.socialrelationships.domain.usecase.FriendRequestCase
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FriendRequestViewModel(private val friendRequestCase: FriendRequestCase) : ViewModel()
{

    fun getFriendRequests(): FirestoreRecyclerOptions<FriendRequest> {
        return friendRequestCase.getFriendRequests()
    }

    fun acceptFriendRequest(request: FriendRequest) = liveData<Boolean> {
        emitSource(friendRequestCase.acceptFriendRequest(request))
    }

    fun declineFriendRequest(request: FriendRequest) = liveData<Boolean> {
        emitSource(friendRequestCase.declineFriendRequest(request))
    }
}