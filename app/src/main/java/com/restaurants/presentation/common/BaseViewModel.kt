package com.restaurants.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.restaurants.presentation.common.livedata.SingleLiveEvent
import com.restaurants.presentation.utils.RxDisposable

open class BaseViewModel : ViewModel() {
    var errorMessage = SingleLiveEvent<String>()
    var uploading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        RxDisposable.unsubscribe(this)
    }
}