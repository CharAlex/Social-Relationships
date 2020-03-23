package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity
import com.alexchar_dev.socialrelationships.presentation.utils.UserNameInputFilter
import com.alexchar_dev.socialrelationships.presentation.utils.hide
import com.alexchar_dev.socialrelationships.presentation.utils.show
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.new_email_account_fragment.*


class SearchFragment : Fragment() {
    private var adapter: UserFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()

        setUpRecyclerView()

        usernameSearchAutocomplete.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty()) {
                    search_result_recyclerview.hide()
                    return true
                }
                //TODO exclude current user username and refactor
                val usersQuery =
                    firestore.collection("users").orderBy("username", Query.Direction.ASCENDING)
                        .startAt(newText).endAt(newText + "\uf8ff").limit(5)

                val users =
                    FirestoreRecyclerOptions.Builder<User>().setQuery(usersQuery, User::class.java)
                        .build()

                if (adapter == null) {
                    adapter = UserFirestoreRecyclerAdapter(users)
                    search_result_recyclerview.adapter = adapter
                    adapter?.startListening()
                } else {
                    adapter?.updateOptions(users)
                }

                search_result_recyclerview.show()
                return true
            }

        })
    }

    private fun setUpRecyclerView() {
        search_result_recyclerview.layoutManager = LinearLayoutManager(context)
    }


    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter?.startListening()
    }

    override fun onPause() {
        super.onPause()
        adapter?.stopListening()
    }
}
