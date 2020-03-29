package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexchar_dev.socialrelationships.R

class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setUsername(username: String, email: String) {
        val userRow = view.findViewById<TextView>(R.id.usernameRow)
        userRow?.text = username
    }


}