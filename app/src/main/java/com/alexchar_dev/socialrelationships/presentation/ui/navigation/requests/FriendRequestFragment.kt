package com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.domain.entity.FriendRequest
import com.alexchar_dev.socialrelationships.presentation.MainActivityViewModel
import com.alexchar_dev.socialrelationships.presentation.utils.minus
import com.alexchar_dev.socialrelationships.presentation.utils.makeToast
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

        adapter = RequestFirestoreRecyclerAdapter(requests, acceptFriendRequest, declineFriendRequest)
        request_recyclerview.adapter = adapter
    }

    private val acceptFriendRequest : (FriendRequest) -> Unit = { request ->
        viewModel.acceptFriendRequest(request).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == true) {
                makeToast("${request.username} added!")
            } else if (it == false){
                makeToast("something went wrong")
            }
        })
    }

    private val declineFriendRequest : (FriendRequest) -> Unit = {request ->
        viewModel.declineFriendRequest(request).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == true) {
                makeToast("${request.username} declined!")
            } else if (it == false){
                makeToast("something went wrong")
            }
        })
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
