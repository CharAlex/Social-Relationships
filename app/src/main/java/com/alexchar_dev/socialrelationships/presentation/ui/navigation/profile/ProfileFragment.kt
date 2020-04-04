package com.alexchar_dev.socialrelationships.presentation.ui.navigation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.auth.AuthActivity
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity
import com.alexchar_dev.socialrelationships.presentation.utils.makeToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.getScopeId


class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()

    companion object {
        const val KEY = "FragmentKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("debug: Profile fragment onCreateView() called")
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        count_tv.text = "0"

        savedInstanceState?.let {
            val savedValue = it.getString(KEY)
            count_tv.text = savedValue ?: "0"
        }

        count_btn.setOnClickListener(View.OnClickListener {
            val current: Int = (count_tv.text as String).toInt()
            count_tv.text = (current + 1).toString()
        })

        //temp
        log_out_button.setOnClickListener {
            val auth = FirebaseAuth.getInstance()

            viewModel.signOut()
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer { signOutResult ->
                    if (signOutResult == true) {
                        val intent = Intent(activity, AuthActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else if (signOutResult == false) {
                        makeToast("Something went wrong")
                    }
                })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, count_tv.text.toString())
    }
}
