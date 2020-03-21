package com.alexchar_dev.socialrelationships.presentation.ui.navigation.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.alexchar_dev.socialrelationships.R
import com.alexchar_dev.socialrelationships.presentation.ui.navigation.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("debug: ${MainActivity.navigationStack}")

//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//
//        }
//

        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

}
