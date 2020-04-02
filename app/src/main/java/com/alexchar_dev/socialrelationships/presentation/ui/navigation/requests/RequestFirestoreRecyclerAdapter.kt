package com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.FriendRequest
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.request_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RequestFirestoreRecyclerAdapter(
    requests: FirestoreRecyclerOptions<FriendRequest>,
    private val handleFriendRequest: (FriendRequest) -> Unit
) : FirestoreRecyclerAdapter<FriendRequest, RequestFirestoreRecyclerAdapter.RequestViewHolder>(requests) {

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int, request: FriendRequest) {

        holder.bind(request, handleFriendRequest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.request_item, parent, false)
        return RequestViewHolder(view)
    }


    inner class RequestViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(request: FriendRequest, handleFriendRequest: (FriendRequest) -> Unit) {
            setView(request)
            view.acceptBtn.setOnClickListener{ handleFriendRequest(request)}
            view.declineBtn.setOnClickListener{ handleFriendRequest(request)}
        }

        private fun setView(request: FriendRequest) {
            val username = view.findViewById<TextView>(R.id.usernameRow)
            val email = view.findViewById<TextView>(R.id.emailRow)
            val timestamp = view.findViewById<TextView>(R.id.timestampRow)

            username.text = request.username
            email.text = "This dude just added you!"
            timestamp.text = request.timestamp.toSimpleString()
        }
    }
    fun Date.toSimpleString() : String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(this)
    }

}