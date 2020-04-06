package com.alexchar_dev.socialrelationships.presentation.ui.navigation.search

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.User
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity
import com.alexchar_dev.socialrelationships.presentation.utils.UserNameInputFilter
import com.alexchar_dev.socialrelationships.presentation.utils.hide
import com.alexchar_dev.socialrelationships.presentation.utils.makeToast
import com.alexchar_dev.socialrelationships.presentation.utils.show
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.new_email_account_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private var adapter: UserFirestoreRecyclerAdapter? = null
    private val viewModel: SearchViewModel by viewModel()

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
//                if(newText.length > 2) { }

                val users = viewModel.getUserSearchResult(newText)

                adapter?.updateOptions(users)

                search_result_recyclerview.show()
                return true
            }

        })
    }

    private fun setUpRecyclerView() {
        search_result_recyclerview.layoutManager = LinearLayoutManager(context)
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val users = viewModel.getUserSearchResult(null)

        //lamda function as last parameter for click listener
        adapter = UserFirestoreRecyclerAdapter(users, currentUserId) { user ->
            viewModel.sendFriendRequest(user.userId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it == true) {
                    makeToast("request sent")
                } else if (it == false)
                {
                    makeToast("something went wrong")
                }

            })
        }

        search_result_recyclerview.adapter = adapter
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
