package com.mock.musictpn.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mock.musictpn.data.network.IMusicService
import kotlinx.coroutines.*
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var musicService: IMusicService

    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String?>()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch { block() }
    }

}