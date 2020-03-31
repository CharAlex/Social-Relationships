package com.alexchar_dev.socialrelationships.data

import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.data.firebase.FirestoreService
import com.alexchar_dev.socialrelationships.data.firebase.firebaseAuth.FirebaseAuthService
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

    override fun sendFriendRequest(userId: String?) {
        firestoreService.sendFriendRequest(userId)
    }

    override fun logIn(email: String, password: String) : MutableLiveData<Boolean> {
        return firebaseAuthService.logIn(email, password)
    }
}