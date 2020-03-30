package com.alexchar_dev.socialrelationships.domain.usecase

import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class SearchUserCaseImpl(private val userRepository: UserRepository) : SearchUserCase{
    override fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        return userRepository.getUserSearchResult(searchTerm)
    }

    override fun sendFriendRequest(userId: String?) {
        userRepository.sendFriendRequest(userId)
    }

}

interface SearchUserCase {
    fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User>
    fun sendFriendRequest(userId: String?)
}