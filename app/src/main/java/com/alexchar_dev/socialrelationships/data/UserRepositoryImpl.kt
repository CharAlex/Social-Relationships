package com.alexchar_dev.socialrelationships.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.data.firebase.FirestoreService
import com.alexchar_dev.socialrelationships.data.firebase.firebaseAuth.FirebaseAuthService
import com.alexchar_dev.socialrelationships.domain.entity.FriendRequest
import com.alexchar_dev.socialrelationships.domain.entity.Friendship
import com.alexchar_dev.socialrelationships.domain.entity.Result
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.domain.repository.UserRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class UserRepositoryImpl(
    private val firebaseAuthService: FirebaseAuthService,
    private val firestoreService: FirestoreService
) : UserRepository {
    override fun observeRegistrationResponse(): MutableLiveData<Boolean> {
        return firebaseAuthService.getRegistrationResponse()
    }

    override fun observeCheckUsernameResponse(): MutableLiveData<Boolean> {
        return firebaseAuthService.getUsernameCheckResponse()
    }

    override fun createUserWithEmail(email: String, password: String, username: String){
        firebaseAuthService.createFirebaseUser(email, password, username)
    }

    override suspend fun isEmailValid(email: String): Result<Boolean> {
        return firebaseAuthService.isEmailValid(email)
    }

    override fun isUsernameTaken(username: String) {
        firebaseAuthService.isUsernameTaken(username)
    }

    override fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        return firestoreService.getUserSearchResult(searchTerm)
    }

    override fun getFriendRequests(): FirestoreRecyclerOptions<FriendRequest> {
        return firestoreService.getFriendRequests()
    }

    override suspend fun sendFriendRequest(userId: String?) : LiveData<Boolean> {
        return firestoreService.sendFriendRequest(userId)
    }

    override fun logIn(email: String, password: String) : MutableLiveData<Boolean> {
        return firebaseAuthService.logIn(email, password)
    }

    override fun signOut(): LiveData<Boolean> {
        return firebaseAuthService.signOut()
    }

    override fun clearPersistence() {
        return firebaseAuthService.clearPersistence()
    }

    override fun acceptFriendRequest(request: FriendRequest): LiveData<Boolean> {
        return firestoreService.acceptFriendRequest(request)
    }

    override fun declineFriendRequest(request: FriendRequest): LiveData<Boolean> {
        return firestoreService.declineFriendRequest(request)
    }

    override suspend fun getCurrentUser(): User? {
        return firestoreService.getCurrentUser()
    }

    override suspend fun getFriendRequest(uid: String, followerUid: String): FriendRequest? {
        return firestoreService.getFriendRequest(uid, followerUid)
    }

    override fun getFriends(): FirestoreRecyclerOptions<Friendship> {
        return firestoreService.getFriends()
    }
}