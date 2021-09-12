package com.mock.musictpn.ui.base

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mock.musictpn.datasource.network.IMusicService
import kotlinx.coroutines.*
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var musicService: IMusicService

    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String?>()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            try {
                block()
            } catch (ex: UnknownHostException) {
                errorMessage.postValue("Check your internet connection and try again !")
            } catch (ex: Exception){
                errorMessage.postValue("ERROR")
            }
        }
    }

    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return CoroutineScope(Dispatchers.IO).async { block() }
    }

    suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
        return async(block).await()
    }

}