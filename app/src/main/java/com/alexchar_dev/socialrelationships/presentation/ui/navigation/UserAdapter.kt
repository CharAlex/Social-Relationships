package com.alexchar_dev.socialrelationships.presentation.ui.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.User

class UserAdapter(context: Context, private val allUsers: List<User>) : ArrayAdapter<User>(context, 0, allUsers) {

    private var suggestions: List<User> = allUsers

    override fun getCount(): Int {
        return suggestions.size
    }

    override fun getItem(position: Int): User? {
        return suggestions[position]
    }

    override fun getItemId(position: Int): Long {
        return suggestions[position].userId.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val userView = convertView ?:  LayoutInflater.from(context).inflate(
            R.layout.user_autocomplete_row, parent, false
        )

        val username = userView.findViewById<ConstraintLayout>(R.id.usernameRow) as TextView

        username.text = getItem(position)?.username

        return userView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                suggestions = filterResults.values as List<User>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    allUsers
                else
                    allUsers.filter {
                        it.username.toLowerCase().contains(queryString)
                    }
                return filterResults
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as User).username
            }
        }
    }
}