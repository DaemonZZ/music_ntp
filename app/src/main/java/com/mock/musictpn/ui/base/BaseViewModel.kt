package com.mock.musictpn.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mock.musictpn.datasource.TrackRepository
import com.mock.musictpn.datasource.local.dao.FavoriteDao
import com.mock.musictpn.datasource.network.IMusicService
import kotlinx.coroutines.*
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var musicService: IMusicService

    @Inject
    lateinit var trackRepository: TrackRepository

    var isLoading = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String?>()

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            try {
                block()
            } catch (ex: UnknownHostException) {
                errorMessage.postValue("Check your internet connection and try again !")
            } catch (ex: Exception) {
                errorMessage.postValue("ERROR ${ex.message}")
            }
        }
    }

    suspend fun <T> asyncOnIO(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return CoroutineScope(Dispatchers.IO).async { block() }
    }

    suspend fun <T> asyncOnIOAwait(block: suspend CoroutineScope.() -> T): T {
        return asyncOnIO(block).await()
    }

}