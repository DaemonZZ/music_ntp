package com.mock.musictpn.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.image.Image
import com.mock.musictpn.model.image.ImageList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():BaseViewModel() {
    private val _albumList = MutableLiveData<AlbumList>()
    private val _images = MutableLiveData<List<Image>>()


    val albumList : LiveData<AlbumList>  get() = _albumList
    val images : LiveData<List<Image>> get() = _images


    fun getAlbums() = launchOnUI{
        val list = musicService.getTopTrendingAlbums(ApiContract.RANGE_WEEK,5)
        _albumList.postValue(list.body()!!)
        for (item in list.body()!!.albums){

        }
    }






    fun getAlbum() = launchOnUI {
        isLoading.postValue(true)
        try {
            val response = musicService.getTopTrendingAlbums(ApiContract.RANGE_WEEK)
            if (response.isSuccessful){
                Log.d("NganHV", "${response.body()}")
                isLoading.postValue(false)
            }
        } catch (e: Exception) {
            errorMessage.postValue(e.message.toString())
        }
    }

}