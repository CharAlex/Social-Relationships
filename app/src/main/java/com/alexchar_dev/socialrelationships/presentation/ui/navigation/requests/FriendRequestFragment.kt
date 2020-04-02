package com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.utils.makeToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_friend_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendRequestFragment : Fragment() {
    private var adapter: RequestFirestoreRecyclerAdapter? = null
    private val viewModel: FriendRequestViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        request_recyclerview.layoutManager = LinearLayoutManager(context)
        val requests = viewModel.getFriendRequests()

        adapter = RequestFirestoreRecyclerAdapter(requests) { request ->
            println("debug: $request")
        }
        request_recyclerview.adapter = adapter
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
