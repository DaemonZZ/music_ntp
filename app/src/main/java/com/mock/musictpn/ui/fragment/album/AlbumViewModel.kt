package com.mock.musictpn.ui.fragment.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor() : BaseViewModel() {
    private val _albumList = MutableLiveData<AlbumList>()
    val albumList: LiveData<AlbumList> get() = _albumList

    fun getAlbums() = launchOnUI {
        val response = musicService.getTopTrendingAlbums(ApiContract.RANGE_MONTH, 20)
        if (response.isSuccessful) {
            response.body()?.let {
                _albumList.postValue(it)
            }
        }

    }
}