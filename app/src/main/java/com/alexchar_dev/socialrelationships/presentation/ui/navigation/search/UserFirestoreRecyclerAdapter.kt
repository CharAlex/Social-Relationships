package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class UserFirestoreRecyclerAdapter (users: FirestoreRecyclerOptions<User>) : FirestoreRecyclerAdapter<User, UserViewHolder>(users) {
    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int, user: User) {
        userViewHolder.setUsername(user.username, user.email)

        userViewHolder.itemView.setOnClickListener {
            println("debug: ${getItem(position).userId}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        //TODO if itemCount <= 0 show something in ui
    }
}