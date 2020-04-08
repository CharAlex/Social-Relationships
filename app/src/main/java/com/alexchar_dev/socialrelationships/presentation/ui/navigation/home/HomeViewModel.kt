package com.alexchar_dev.socialrelationships.presentation.ui.navigation.home

import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.entity.Friendship
import com.alexchar_dev.socialrelationships.domain.usecase.FriendshipCase
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class HomeViewModel(private val friendshipCase: FriendshipCase) : ViewModel() {

    fun getFriends(): FirestoreRecyclerOptions<Friendship> {
        return friendshipCase.getFriends()
    }
}