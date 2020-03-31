package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity
import com.alexchar_dev.socialrelationships.presentation.utils.makeToast
import com.alexchar_dev.socialrelationships.presentation.utils.textToString
import kotlinx.android.synthetic.main.fragment_log_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : Fragment() {
    private val viewModel: LogInViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logInBtn.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when(view.id) {
            R.id.logInBtn -> {
                viewModel.logIn(user_email.textToString, user_password.textToString).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it == true) {
                        makeToast("Log in succesful")
                        startMainActivity()
                    } else if (it == false)
                    {
                        makeToast("wrong credentials")
                    }

                })
            }
        }
    }

    private fun startMainActivity() {
        activity?.let {
            val intent = Intent(it, MainActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }
}
