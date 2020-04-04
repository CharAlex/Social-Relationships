package com.alexchar_dev.socialrelationships.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class MainActivityViewModel : ViewModel() {

    val badgeCount: MutableLiveData<Int> = MutableLiveData(0)

}