package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alexchar_dev.socialrelationships.R
import kotlinx.android.synthetic.main.fragment_auth_home.*
import kotlinx.android.synthetic.main.fragment_auth_home.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.time.measureDuration
import kotlin.system.measureTimeMillis


class AuthHomeFragment : Fragment() {

    private val viewModel: AuthHomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Handle email input
        with(user_email) {
            addTextChangedListener(object:TextWatcher{
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    view.create_account_button.isEnabled = !p0.isNullOrEmpty() && p0.isValidEmail()
                }
            })
            setOnKeyListener(object:View.OnKeyListener{
                override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p2?.action == KeyEvent.ACTION_DOWN) {
                        when (p1) {
                            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                                if(view.user_email.text.isNullOrEmpty()) return false
                                view.create_account_button.callOnClick()
                                return true
                            }
                        }
                    }
                    return false
                }

            })
        }

        //start new fragment for username, password
        create_account_button.setOnClickListener {
            var isEmailValid = false
            lifecycleScope.launch {
                progress_circular.visibility = View.VISIBLE
                withContext(Dispatchers.IO){
                    isEmailValid = viewModel.isEmailValid(user_email.text.toString())
                }

                if(isEmailValid) {
                    withContext(Dispatchers.Main){
                        completeRegistrationFragment()
                    }
                } else {
                    progress_circular.visibility = View.GONE
                    user_email.error = "Email is already in use"
                }
            }


        }


    }
    private fun completeRegistrationFragment() {
        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                NewEmailAccountFragment.newInstance(user_email.text.toString())
            )
            .addToBackStack(NewEmailAccountFragment().tag)
            .commit()
    }
    //TODO move to viewmodel
    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
