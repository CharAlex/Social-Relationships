package com.alexchar_dev.socialrelationships.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.domain.entity.FriendRequest
import com.alexchar_dev.socialrelationships.domain.entity.Result
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions

interface UserRepository {
    fun createUserWithEmail(email: String, password: String, username: String)

    fun observeRegistrationResponse() : MutableLiveData<Boolean>
    fun observeCheckUsernameResponse() : MutableLiveData<Boolean>

    suspend fun isEmailValid(email: String) : Result<Boolean>
    fun isUsernameTaken(username: String)
    fun getUserSearchResult(searchTerm: String?) : FirestoreRecyclerOptions<User>

    fun getFriendRequests() : FirestoreRecyclerOptions<FriendRequest>
    suspend fun getFriendRequest(uid: String, followerUid: String) : FriendRequest?

    suspend fun sendFriendRequest(userId: String?) : LiveData<Boolean>

    fun logIn(email: String, password: String) : MutableLiveData<Boolean>
    fun signOut() : LiveData<Boolean>

    fun clearPersistence()

    fun acceptFriendRequest(request: FriendRequest) : LiveData<Boolean>
    fun declineFriendRequest(request: FriendRequest) : LiveData<Boolean>

    suspend fun getCurrentUser(): User?
}