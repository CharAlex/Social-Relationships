package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.user_item.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var adapter: UserFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("debug: ${MainActivity.navigationStack}")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        search_result_recyclerview.layoutManager = LinearLayoutManager(context)
        val firestore = FirebaseFirestore.getInstance()
        val usersQuery = firestore.collection("users").orderBy("username", Query.Direction.ASCENDING)

        val users = FirestoreRecyclerOptions.Builder<User>().setQuery(usersQuery, User::class.java).build()

        adapter = UserFirestoreRecyclerAdapter(users)
        search_result_recyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    private inner class UserViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) { //TODO refactor this from fragment to according classes
        internal fun setUsername(username: String, email: String) {
            val userRow = view.findViewById<TextView>(R.id.usernameRow)
            val emailRow = view.findViewById<TextView>(R.id.emailRow)
            userRow?.text = username
            emailRow?.text = email
        }
    }

    private inner class UserFirestoreRecyclerAdapter internal constructor(users: FirestoreRecyclerOptions<User>) : FirestoreRecyclerAdapter<User, UserViewHolder>(users) {
        override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int, user: User) {
            userViewHolder.setUsername(user.username, user.email)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
            return UserViewHolder(view)
        }
    }
}
