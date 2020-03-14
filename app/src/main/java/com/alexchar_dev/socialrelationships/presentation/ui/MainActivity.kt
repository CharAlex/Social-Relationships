package com.alexchar_dev.socialrelationships.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //temp
        log_out_button.setOnClickListener {
            //TODO check with auth.currentUser
            val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
