package com.alexchar_dev.socialrelationships.presentation.utils

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Int>.minus(num: Int) {
    this.value = this.value?.minus(num)
}

fun MutableLiveData<Int>.plus(num: Int) {
    this.value = this.value?.plus(num)
}