package com.alexchar_dev.socialrelationships.presentation.utils

import com.firebase.ui.firestore.ObservableSnapshotArray
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot

class EmptySnapshotArray<T>: ObservableSnapshotArray<T>(SnapshotParser<T> {
    TODO()
}) {
    override fun getSnapshots(): MutableList<DocumentSnapshot> {
        return mutableListOf()
    }
}