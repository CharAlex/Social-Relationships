package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.domain.usecase.SearchUserCase
import com.alexchar_dev.socialrelationships.presentation.utils.EmptySnapshotArray
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SearchViewModel(private val searchUseCase: SearchUserCase) : ViewModel()
{
    var searchTerm = MutableLiveData<String>()


    private val firestore = FirebaseFirestore.getInstance()

    fun getUserSearchResult(): FirestoreRecyclerOptions<User> {
        val usersQuery =
            firestore.collection("users").orderBy("username", Query.Direction.ASCENDING)
                .startAt(searchTerm.value).endAt(searchTerm.value + "\uf8ff").limit(5)

        if(searchTerm.value.isNullOrBlank()) return FirestoreRecyclerOptions.Builder<User>().setSnapshotArray(
            EmptySnapshotArray()
        ).build()
        return FirestoreRecyclerOptions.Builder<User>().setQuery(usersQuery, User::class.java).build()
    }

}

