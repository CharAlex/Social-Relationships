package com.alexchar_dev.socialrelationships.presentation.utils

import android.app.Activity
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.text.SimpleDateFormat
import java.util.*

fun View.hide() = run { this.visibility = View.GONE }
fun View.show() = run { this.visibility = View.VISIBLE }

val TextView.textToString : String
    get() = this.text.toString()

fun Button.disable() = run { this.isEnabled = false }
fun Button.enable() = run { this.isEnabled = true }

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Fragment.makeToast(value: String) {
    Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
}

fun Activity.attachFragment(manager: FragmentManager, containerId: Int, view: Fragment, tag: String) {
    manager.beginTransaction()
        .replace(containerId, view, tag)
        .commitNowAllowingStateLoss()
}

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd/MM/yyy")
    return format.format(this)
}