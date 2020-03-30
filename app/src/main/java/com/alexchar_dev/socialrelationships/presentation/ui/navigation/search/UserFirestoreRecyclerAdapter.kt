package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.user_item.view.*

class UserFirestoreRecyclerAdapter (users: FirestoreRecyclerOptions<User>, private val currentUserId: String, private val listener: (User) -> Unit) : FirestoreRecyclerAdapter<User, UserFirestoreRecyclerAdapter.UserViewHolder>(users) {
    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int, user: User) {
        if(user.requestIds.contains(currentUserId)) {
            userViewHolder.itemView.addUserBtn.text = "Sent"
        } else {
            userViewHolder.itemView.addUserBtn.text = "Add"
        }
        userViewHolder.bind(user, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        //TODO if itemCount <= 0 show something in ui
    }

    inner class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private fun setUsername(username: String, email: String) {
            val userRow = view.findViewById<TextView>(R.id.usernameRow)
            userRow?.text = username
        }

        fun bind(user: User, listener: (User) -> Unit)
        {
            setUsername(user.username, user.email)
            view.addUserBtn.setOnClickListener {//TODO if the user that was just added and all the previous users that contains the current logged in user in their requestIds field then show the different button
                listener(user)
            }
        }

//        private fun changeButtonUI() {
//            val button = (view.addUserBtn as MaterialButton)
//            button.text = "Added"
//            button.apply {
//                text = "Added"
//                setIconTintResource(R.color.buttonPrimaryBlue)
//            }
//            button.icon = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                view.context.resources.getDrawable(R.drawable.ic_check)
//            } else {
//                view.context.resources.getDrawable(R.drawable.ic_check, null);
//            }
//        }
    }
}