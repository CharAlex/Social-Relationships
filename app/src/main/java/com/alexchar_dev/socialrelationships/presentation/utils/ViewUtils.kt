package com.alexchar_dev.socialrelationships.presentation.utils

import android.view.View

fun View.hide() = run { this.visibility = View.GONE }
fun View.show() = run { this.visibility = View.VISIBLE }