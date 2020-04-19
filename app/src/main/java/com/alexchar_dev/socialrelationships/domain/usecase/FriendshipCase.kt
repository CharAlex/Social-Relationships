package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.entity.Friendship
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FriendshipCaseImpl(private val userRepository: UserRepository) : FriendshipCase {
    override fun getFriends(): FirestoreRecyclerOptions<Friendship> {
        return userRepository.getFriends()
    }
}

interface FriendshipCase {
    fun getFriends() : FirestoreRecyclerOptions<Friendship>
}