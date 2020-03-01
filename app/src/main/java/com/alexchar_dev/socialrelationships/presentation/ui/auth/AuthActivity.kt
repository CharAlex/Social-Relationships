package com.alexchar_dev.socialrelationships.presentation.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexchar_dev.socialrelationships.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container,
                AuthHomeFragment()
            )
            .commit()
    }

}