package com.alexchar_dev.socialrelationships.presentation.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.home.HomeFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.notification.NotificationFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.profile.ProfileFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.search.SearchFragment
import com.alexchar_dev.socialrelationships.presentation.utils.NavigationStack
import com.alexchar_dev.socialrelationships.presentation.utils.attachFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationStack.push(R.id.home_nav)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentNavContainer, HomeFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            navigationStack.push(item.itemId)

            if(bottomNavigationView.selectedItemId == item.itemId) return@setOnNavigationItemSelectedListener true

            when(item.itemId) {
                R.id.home_nav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentNavContainer, HomeFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
                R.id.search_nav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentNavContainer, SearchFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
                R.id.notification_nav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentNavContainer, NotificationFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
                R.id.profile_nav -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentNavContainer, ProfileFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
            }
            true
        }

    }

    override fun onBackPressed() {
        navigationStack.pop()
        val nextNavigation = navigationStack.peek()

        if(nextNavigation != null) {
            bottomNavigationView.selectedItemId = nextNavigation as Int
        }
        else {
            super.onBackPressed()
        }
    }

    companion object ActivityStack {
        val navigationStack = NavigationStack()
    }
}
