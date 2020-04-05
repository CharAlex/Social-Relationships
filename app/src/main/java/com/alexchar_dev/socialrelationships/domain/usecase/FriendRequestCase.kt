package com.alexchar_dev.socialrelationships.domain.usecase

import androidx.lifecycle.LiveData
import com.alexchar_dev.socialrelationships.domain.entity.FriendRequest
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class FriendRequestCaseImpl(private val userRepository: UserRepository) : FriendRequestCase{
    override fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        return userRepository.getUserSearchResult(searchTerm)
    }

    override fun getFriendRequests(): FirestoreRecyclerOptions<FriendRequest> {
       return userRepository.getFriendRequests()
    }

    override suspend fun sendFriendRequest(userId: String?) : LiveData<Boolean>{
        return userRepository.sendFriendRequest(userId)
    }

    override fun acceptFriendRequest(request: FriendRequest): LiveData<Boolean> {
        return  userRepository.acceptFriendRequest(request)
    }

    override fun declineFriendRequest(request: FriendRequest): LiveData<Boolean> {
        return  userRepository.declineFriendRequest(request)
    }

    override suspend fun getCurrentUser(): User? {
        return userRepository.getCurrentUser()
    }

    override suspend fun getFriendRequest(uid: String, followerUid: String): FriendRequest? {
        return userRepository.getFriendRequest(uid, followerUid)
    }
}

interface FriendRequestCase {
    fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User>
    fun getFriendRequests(): FirestoreRecyclerOptions<FriendRequest>
    suspend fun sendFriendRequest(userId: String?) : LiveData<Boolean>
    fun acceptFriendRequest(request: FriendRequest) : LiveData<Boolean>
    fun declineFriendRequest(request: FriendRequest) : LiveData<Boolean>
    suspend fun getCurrentUser(): User?
    suspend fun getFriendRequest(uid: String, followerUid: String): FriendRequest?
}