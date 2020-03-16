package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.utils.UserNameInputFilter
import com.alexchar_dev.socialrelationships.presentation.utils.usernameCharacters
import kotlinx.android.synthetic.main.new_email_account_fragment.*
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
    private var isUsernameAvailable : Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.new_email_account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_email_display.text = Email
        usernameLayout.error = " " //add empty error to add the appropriate padding

        viewModel.registrationResponse.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { accountCreated ->
                if (accountCreated == true) {
                    Toast.makeText(context, "Account Created!", Toast.LENGTH_LONG).show()
                } else if (accountCreated == false) {
                    Toast.makeText(context, "Weak password!", Toast.LENGTH_LONG).show()
                    create_user_button.isEnabled = true
                }
            })

        viewModel.checkUsernameResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it == false) {
                    isUsernameAvailable = false
                    username.setCompoundDrawables(null, null, errorIcon, null)
                    usernameLayout.error = getString(R.string.username_not_available)
                } else if (it == true){
                    username.setCompoundDrawables(null, null, successIcon, null)
                    isUsernameAvailable = true
                }

        })

        create_user_button.setOnClickListener {
            create_user_button.isEnabled = false
            viewModel.createUser(
                user_email_display.text.toString(),
                password.text.toString(),
                username.text.toString()
            )
            val sp = activity?.getSharedPreferences("auth", Context.MODE_PRIVATE)
            sp?.edit()?.putBoolean("logged", true)
                ?.apply() // probably dont need in case i check with auth.currentUser if is null or not
        }

        //TODO confirm password check and done button check username password valid

        username.apply {
            var checkRequired = true
            filters = arrayOf<InputFilter>(UserNameInputFilter(), InputFilter.LengthFilter(30))
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int ) {
                    checkRequired = s.isUsernameCharacters()
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    filterUsername(s)
                }

                override fun afterTextChanged(s: Editable?) {
                    //no need to check username availability when user types invalid characters
                    if(checkRequired) checkUsernameTaken(s)
                }
            })

            setOnFocusChangeListener{ _, hasFocus ->
                if (!hasFocus) {
                    view.usernameLayout.error = ""
                } else if (hasFocus && !isUsernameAvailable) {
                    view.usernameLayout.error = getString(R.string.username_not_available)
                }
            }
        }

        //Align the error icon in the password input (due to textInputLayout error adding padding in the textInput)
        passwordLayout.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                passwordLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                alignErrorIconInTextInput()
            }
        })

        password.setOnFocusChangeListener { _, hasFocus ->

            val isPasswordInvalid = password.text.toString().length in 1..5
            if (!hasFocus && isPasswordInvalid) {
                password_error_icon.visibility = View.VISIBLE
                passwordLayout.error = " "
            } else if (hasFocus && isPasswordInvalid) {
                passwordLayout.error = getString(R.string.weak_password)
            }
        }

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length > 5) {
                    password_error_icon.visibility = View.GONE
                    passwordLayout.error = ""
                }
            }
        })
    }

    private fun alignErrorIconInTextInput() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(email_registration_layout)
        val marginTop = (password.height - password_error_icon.height) / 2
        println("debug: $marginTop")
        constraintSet.setMargin(
            R.id.password_error_icon,
            ConstraintSet.TOP,
            marginTop
        )
        constraintSet.applyTo(email_registration_layout)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.clearLiveData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("userEmail")?.let {
            Email = it
        }
    }

    private fun Char.isUsernameCharacters(): Boolean = usernameCharacters.contains(this)

    private fun filterUsername(s: CharSequence) {
        val invalidCharacters = s.filter { c -> !c.isUsernameCharacters() }

        val isUsernameValid = invalidCharacters.isEmpty()

        if (isUsernameValid && !wasUsernameInvalid) {
            usernameLayout.error = null
        }

        if (!isUsernameValid) {
            usernameLayout.error = getString(R.string.wrong_username)

            username.apply {
                wasUsernameInvalid = true
                setText(s.filter { c -> !invalidCharacters.contains(c) })
                setSelection(username.text?.length ?: 0)
            }
        } else {
            wasUsernameInvalid = false
        }
    }

    private fun CharSequence?.isUsernameCharacters() : Boolean {
        if(this.isNullOrEmpty()) return true
        val invalidCharacters = this.filter { c -> !c.isUsernameCharacters() }
        return invalidCharacters.isEmpty()
    }

    private fun checkUsernameTaken(s: Editable?) {
        timer.cancel()
        val sleep = when (s?.length) {
            1, 2, 3, 4, 5 -> 1500L
            else -> 100L
        }

        if (s.isNullOrEmpty()) return
        timer = Timer()

        timer.schedule(sleep) {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    //sets livedata in firebaseauth
                    viewModel.checkUsernameTaken(s.toString())
                }
            }
        }
    }

    private val errorIcon : Drawable?
    get()
    {
        val errorIcon: Drawable? = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context?.resources?.getDrawable(R.drawable.ic_error_text_input)
        } else {
            context?.resources?.getDrawable(R.drawable.ic_error_text_input, null);
        }
        errorIcon?.setBounds(0, 0, resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        return errorIcon
    }

    private val successIcon : Drawable?
        get()
        {
        val successIcon: Drawable? = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context?.resources?.getDrawable(R.drawable.ic_success_text_input)
        } else {
            context?.resources?.getDrawable(R.drawable.ic_success_text_input, null);
        }
        successIcon?.setBounds(0, 0, resources.getDimensionPixelSize(R.dimen.icon_size), resources.getDimensionPixelSize(R.dimen.icon_size))
        return successIcon
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) = NewEmailAccountFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }
    //TODO show warning if password is simple
}
