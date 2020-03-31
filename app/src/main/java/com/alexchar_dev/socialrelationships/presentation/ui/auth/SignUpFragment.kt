package com.alexchar_dev.socialrelationships.presentation.ui.auth


import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity
import com.alexchar_dev.socialrelationships.presentation.utils.*
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

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModel()
    private var wasUsernameInvalid = false
    var timer = Timer()
    private var isUsernameAvailable : Boolean? = null

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
        passwordLayout.error = " "

        viewModel.registrationResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { accountCreated ->
                if (accountCreated == true) {
                    makeToast("Account Created!")
                    startMainActivity()
                } else if (accountCreated == false) {
                    makeToast("An error occurred during your account setup!")
                    create_user_button.enable()
                }
            })

        viewModel.checkUsernameResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it == false) {
                    handleUsernameResponse(false, errorIcon)
                    usernameLayout.error = getString(R.string.username_not_available)
                } else if (it == true){
                    handleUsernameResponse(true, successIcon)
                }

        })

        create_user_button.setOnClickListener {
            if (validateFormInputs(view)) return@setOnClickListener

            create_user_button.disable()
            create_account_progress_bar.show()
            create_user_button.text = ""

            viewModel.createUser(user_email_display.textToString, password.textToString, username.textToString)
        }

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
                    if(checkRequired) checkUsernameTaken(s, view)
                }
            })

            setOnFocusChangeListener{ _, hasFocus ->
                if (!hasFocus) {
                    view.usernameLayout.error = ""
                } else if (hasFocus && isUsernameAvailable == false ) {
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
            val isPasswordInvalid = password.textToString.length in 1..5
            if (!hasFocus && (isPasswordInvalid || password.textToString.isEmpty())) {
                password_error_icon.show()
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
                    password_error_icon.hide()
                    passwordLayout.error = ""
                }
            }
        })
    }

    private fun handleUsernameResponse(isAvailable: Boolean, icon: Drawable?) {
        isUsernameAvailable = isAvailable
        create_account_progress_bar.hide()
        create_user_button.enable()
        create_user_button.text = "DONE"
        username.setCompoundDrawables(null, null, icon, null)
    }

    private fun startMainActivity() { //TODO or call auth activity
        activity?.let {
            val intent = Intent(it, MainActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }
    }

    private fun validateFormInputs(view: View): Boolean {
        var valid = false
        if (isUsernameAvailable == false || view.username.textToString.isEmpty()) {
            showUsernameError(view)
            valid = true
        }
        if (view.password.textToString.length < 6 || view.password.textToString.isEmpty()) {
            showPasswordError(view)
            valid = true
        }

        return valid
    }

    private fun showUsernameError(view: View) {
        view.username.setCompoundDrawables(null, null, errorIcon, null)
        view.usernameLayout.error = getString(R.string.username_not_available)
    }

    private fun showPasswordError(view: View) {
        view.password_error_icon.show()
        view.passwordLayout.error = getString(R.string.weak_password)
    }

    private fun alignErrorIconInTextInput() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(email_registration_layout)
        val marginTop = (password.height - password_error_icon.height) / 2
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

    private fun checkUsernameTaken(s: Editable?, view: View) {
        println("debug: canceling timer $timer")
        timer.cancel()
        val sleep = when (s?.length) {
            1, 2, 3 -> 1000L
            else -> 500L
        }
        if(s.isNullOrBlank()) {
            view.username.setCompoundDrawables(null, null, null, null)
            view.create_account_progress_bar.hide()
            view.create_user_button.text = "DONE"
            view.create_user_button.enable()
            return
        }

        timer = Timer()
        println("debug: new timer $timer")

        view.create_account_progress_bar.show()
        view.create_user_button.text = ""
        view.create_user_button.disable()

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
        fun newInstance(param1: String) = SignUpFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }
    //TODO show warning if password is simple
}
