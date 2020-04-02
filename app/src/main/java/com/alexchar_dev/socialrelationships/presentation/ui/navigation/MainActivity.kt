package com.alexchar_dev.socialrelationships.presentation.ui.navigation

import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.home.HomeFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.notification.NotificationFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.profile.ProfileFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.requests.FriendRequestFragment
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.search.SearchFragment
import com.alexchar_dev.socialrelationships.presentation.utils.NavigationStack
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var savedStateSparseArray = SparseArray<Fragment.SavedState>()
    private var currentSelectItemId = R.id.home_nav
    var fragment: Fragment? = null
    var friendRequests = 0 //TODO move to viewmodel

    companion object {
        val navigationStack = NavigationStack()
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentNavContainer, HomeFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
        navigationStack.push(R.id.home_nav)

        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(fragmentSwapNavigationItemSelectedListener)
        observeFriendRequest()
    }

    private fun observeFriendRequest() {
//        TODO move from here
        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser!!.uid

        firestore.collection("users").document(currentUserId).collection("requests").whereEqualTo("seen", false)
            .addSnapshotListener { snapshots, exception ->
                if (exception != null) {
                    println("debug: listen:error $exception")
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {

                        DocumentChange.Type.ADDED -> {
                            computeNotificationBadge(++friendRequests)
                        }
                        DocumentChange.Type.MODIFIED -> Toast.makeText(this,"friend modified request!", Toast.LENGTH_SHORT).show()
                        DocumentChange.Type.REMOVED -> if(--friendRequests > 0 ) computeNotificationBadge(--friendRequests) else removeNotificationBadge()
                    }
                }
            }
    }

    private fun removeNotificationBadge() {
        bottomNavigationView.removeBadge(R.id.notification_nav)
    }

    private fun computeNotificationBadge(count: Int) {
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.request_nav).setIcon(R.drawable.ic_people)

        bottomNavigationView.getOrCreateBadge(R.id.request_nav).apply {
            backgroundColor = Color.RED
            badgeTextColor = Color.WHITE
            maxCharacterCount = 3
            number = count
            isVisible = true
        }
    }


    private val fragmentSwapNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        navigationStack.push(item.itemId)

        when(item.itemId) {
            R.id.request_nav -> {
                swapFragments(item.itemId, "Request")
                return@OnNavigationItemSelectedListener true
            }
            R.id.search_nav -> {
                swapFragments(item.itemId, "Search")
                return@OnNavigationItemSelectedListener true
            }
            R.id.home_nav -> {
                swapFragments(item.itemId, "Home")
                return@OnNavigationItemSelectedListener true
            }
            R.id.notification_nav -> {
                swapFragments(item.itemId, "Notification")
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_nav -> {
                swapFragments(item.itemId, "Profile")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun swapFragments(itemId: Int, key: String) {
        if(supportFragmentManager.findFragmentByTag(key) == null) { //if next navigation is not the already selected then save state and navigate to that fragment
            savedFragmentState(itemId)
            createFragment(key, itemId)
        }
    }

    private fun createFragment(key: String, itemId: Int) {
        var fragment: Fragment = Fragment()
        when (key) {
            "Request" -> {
                fragment = FriendRequestFragment()
            }
            "Search" -> {
                fragment = SearchFragment()
            }
            "Home" -> {
                fragment = HomeFragment()
            }
            "Notification" -> {
                fragment = NotificationFragment()
            }
            "Profile" -> {
                fragment = ProfileFragment()
            }
        }

        fragment.setInitialSavedState(savedStateSparseArray[itemId])

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentNavContainer, fragment, key)
            .commit()
    }

    private fun savedFragmentState(itemId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentNavContainer)
        if(currentFragment != null) {
            val savedState = supportFragmentManager.saveFragmentInstanceState(currentFragment)
            savedStateSparseArray.put(currentSelectItemId, savedState)
        }
        currentSelectItemId = itemId
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
}
