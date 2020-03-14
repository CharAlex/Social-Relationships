package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.utils.UserNameInputFilter
import com.alexchar_dev.socialrelationships.presentation.utils.usernameCharacters
import kotlinx.android.synthetic.main.new_email_account_fragment.*
import kotlinx.android.synthetic.main.new_email_account_fragment.username
import kotlinx.android.synthetic.main.new_email_account_fragment.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule

private const val ARG_PARAM1 = "userEmail"
private var Email: String? = null

class NewEmailAccountFragment : Fragment() {

    private val viewModel: NewEmailAccountViewModel by viewModel()
    private var wasUsernameInvalid = false
    var timer = Timer()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_email_account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_email_display.text = Email

        viewModel.observeRegistrationResponse().observe(viewLifecycleOwner, androidx.lifecycle.Observer { accountCreated ->
            if(accountCreated == true) {
                Toast.makeText(context,"Account Created!", Toast.LENGTH_LONG).show()
            } else if (accountCreated == false) {
                Toast.makeText(context, "Weak password!", Toast.LENGTH_LONG).show()
                create_user_button.isEnabled = true
            }
        })

        create_user_button.setOnClickListener{
            create_user_button.isEnabled = false
            viewModel.createUser(user_email_display.text.toString(), password.text.toString(), username.text.toString())
            val sp = activity?.getSharedPreferences("auth", Context.MODE_PRIVATE)
            sp?.edit()?.putBoolean("logged", true)?.apply() // probably dont need in case i check with auth.currentUser if is null or not
        }

        //TODO confirm password check and done button check username password valid

        username.apply {
            filters = arrayOf<InputFilter>(UserNameInputFilter(), InputFilter.LengthFilter(30))
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { filterUsername(s) }
                override fun afterTextChanged(s: Editable?) { checkUsernameTaken(s) }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("userEmail")?.let {
            Email = it
        }
    }

    private fun Char.isUsernameCharacters(): Boolean = usernameCharacters.contains(this)

    private fun filterUsername(s: CharSequence) {
        val invalidCharacters = s.filter { c -> !c.isUsernameCharacters()}

        val isUsernameValid = invalidCharacters.isEmpty()

        if(isUsernameValid && !wasUsernameInvalid) {
            wrong_username.visibility = View.GONE // or could animate
        }

        if(!isUsernameValid) {
            if(!wrong_username.isShown)
                wrong_username.apply {
                    visibility = View.VISIBLE
                    alpha = 0.0f

                    animate()
                        .translationY(0.0f)
                        .alpha(1.0f)
                }

            username.apply {
                wasUsernameInvalid = true
                setText(s.filter { c -> !invalidCharacters.contains(c)})
                setSelection(username.text.length)
            }
        } else {
            wasUsernameInvalid = false
        }
    }

    private fun checkUsernameTaken(s: Editable?) {
        //TODO check if username was changed or its same
        timer.cancel()
        val sleep = when (s?.length) {
            1, 2, 3, 4, 5 -> 1500L
            else -> 100L
        }

        if (s.isNullOrEmpty()) return
        timer = Timer()

        timer.schedule(sleep) {
            var isTaken = false
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    isTaken = viewModel.isUsernameTaken(s.toString())
                }
                withContext(Dispatchers.Main) {
                    if (isTaken) username.error = "username: ${s.toString()} is taken"
                }
            }
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
