package com.mock.musictpn.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mock.musictpn.datasource.network.IMusicService
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

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return CoroutineScope(Dispatchers.IO).async { block() }
    }

    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
        return async(block).await()
    }

}