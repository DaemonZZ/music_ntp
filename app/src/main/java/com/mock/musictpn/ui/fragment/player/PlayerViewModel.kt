package com.mock.musictpn.ui.fragment.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : BaseViewModel() {

    private val _trackList = MutableLiveData<TrackList>()

    fun getTrackList():LiveData<TrackList> =  _trackList

    suspend fun loadAlbum(id:String){
        val list = musicService.getTracksListByAlbumId(id)
        _trackList.postValue(list)
    }

}