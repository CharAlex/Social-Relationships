package com.alexchar_dev.socialrelationships.presentation.ui.navigation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.Friendship
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.friendship_item.view.*

class FriendFirestoreRecyclerAdapter(
    friends: FirestoreRecyclerOptions<Friendship>
) : FirestoreRecyclerAdapter<Friendship, FriendFirestoreRecyclerAdapter.FriendViewHolder>(friends){

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int, friend: Friendship) {
        holder.bind(friend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friendship_item, parent, false)
        return FriendViewHolder(view)

    }

    inner class FriendViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(friend: Friendship) {
            view.username.text = friend.username
        }

    }

}