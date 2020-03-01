package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.alexchar_dev.socialrelationships.R
import kotlinx.android.synthetic.main.fragment_auth_home.*
import kotlinx.android.synthetic.main.new_email_account_fragment.*
import kotlinx.android.synthetic.main.new_email_account_fragment.view.*

private const val ARG_PARAM1 = "userEmail"
private var EMAIL: String? = null

class NewEmailAccountFragment : Fragment() {

    private lateinit var viewModel: NewEmailAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_email_account_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_email_display.text = EMAIL
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewEmailAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("userEmail")?.let {
            EMAIL = it
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) = NewEmailAccountFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }

}