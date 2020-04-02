package com.alexchar_dev.socialrelationships.domain.usecase

import androidx.lifecycle.LiveData
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class SearchUserCaseImpl(private val userRepository: UserRepository) : SearchUserCase{
    override fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        return userRepository.getUserSearchResult(searchTerm)
    }

    override suspend fun sendFriendRequest(userId: String?) : LiveData<Boolean>{
        return userRepository.sendFriendRequest(userId)
    }

}

interface SearchUserCase {
    fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User>
    suspend fun sendFriendRequest(userId: String?) : LiveData<Boolean>
}