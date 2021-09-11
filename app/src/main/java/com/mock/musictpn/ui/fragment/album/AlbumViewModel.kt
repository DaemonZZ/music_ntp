package com.mock.musictpn.ui.fragment.album

import android.util.Log
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor() : BaseViewModel() {

    fun getAlbum() = launchOnUI {
        isLoading.postValue(true)
        try {
            val response = musicService.getTopTrendingAlbums(ApiContract.RANGE_MONTH)
            if (response.isSuccessful){
                Log.d("NganHV", "${response.body()}")
                isLoading.postValue(false)
            }
        } catch (e: Exception) {
            errorMessage.postValue(e.message.toString())
        }
    }

}