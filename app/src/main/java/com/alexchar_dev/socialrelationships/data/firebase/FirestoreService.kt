package com.alexchar_dev.socialrelationships.data.firebase

import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.presentation.utils.EmptySnapshotArray
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebase = FirebaseAuth.getInstance()

    fun getUserSearchResult(searchTerm: String?): FirestoreRecyclerOptions<User> {
        if(searchTerm.isNullOrBlank())
            return FirestoreRecyclerOptions.Builder<User>().setSnapshotArray(EmptySnapshotArray()).build()

        val usersQuery =
            firestore.collection("users").orderBy("username", Query.Direction.ASCENDING)
                .startAt(searchTerm).endAt(searchTerm + "\uf8ff").limit(5)


        return FirestoreRecyclerOptions.Builder<User>().setQuery(usersQuery, User::class.java).build()
    }

    fun sendFriendRequest(userId: String?): Boolean {
        val curUserId = firebase.currentUser!!.uid
        if (userId.isNullOrBlank())
            return false
        val request = hashMapOf<String,String>(
            "avatar" to "default/path",
            "uid" to curUserId
        )
        firestore.collection("users").document(userId).update("requestIds", FieldValue.arrayUnion(curUserId)).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                println("debug: friend request sent")
            } else {

                println("debug: something went wrong ${task.result}")
            }
        }
        //temp
        return true
    }
}