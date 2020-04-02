package com.alexchar_dev.socialrelationships.data.firebase

import androidx.lifecycle.MutableLiveData
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.presentation.utils.EmptySnapshotArray
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebase = FirebaseAuth.getInstance()
    val curUserId = firebase.currentUser?.uid

    fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        if(searchTerm.isNullOrBlank())
            return FirestoreRecyclerOptions.Builder<User>().setSnapshotArray(EmptySnapshotArray()).build()

        val usersQuery =
            firestore.collection("users").orderBy("username", Query.Direction.ASCENDING).startAt(searchTerm).endAt(searchTerm + "\uf8ff").limit(5)

        return FirestoreRecyclerOptions.Builder<User>().setQuery(usersQuery, User::class.java).build()
    }

    suspend fun sendFriendRequest(userId: String?): MutableLiveData<Boolean> {
        val user = getCurrentUser()
        val result: MutableLiveData<Boolean> = MutableLiveData()

        if (user != null && curUserId != null) {
            if (userId.isNullOrBlank()) {
                result.postValue(false)
                return result
            }

            val request = hashMapOf(
                "avatar" to "default/path",
                "uid" to curUserId,
                "username" to user.username,
                "seen" to false,
                "timestamp" to FieldValue.serverTimestamp()
            )

            val userDocResult = addFriendIdToUserDoc(userId)

            if(!userDocResult) {
                result.value = false
                return result
            }

            firestore.collection("users").document(userId).collection("requests")
                .document(curUserId).set(
                    request
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.postValue(true)
                    } else {
                        result.postValue(false)
                    }
                }

        }
        return result
    }

    private suspend fun addFriendIdToUserDoc(userId: String) : Boolean{
        var result: Boolean = false
        firestore.collection("users").document(userId).update("requestIds", FieldValue.arrayUnion(curUserId)).addOnCompleteListener { task ->
            result = task.isSuccessful
        }.await()
        return result
    }

    private suspend fun getCurrentUser() : User? {
        var user: User? = null

        if(curUserId != null)
        firestore.collection("users").document(curUserId).get().addOnSuccessListener {
            user = it.toObject(User::class.java)
        }.await()
        println("debug: the user ${user?.username}")
        return user
    }
}