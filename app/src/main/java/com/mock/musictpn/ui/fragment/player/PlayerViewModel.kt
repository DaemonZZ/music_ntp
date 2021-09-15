package com.mock.musictpn.ui.fragment.player

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : BaseViewModel() {

    private val _trackList = MutableLiveData<TrackList>()  //Current list
    private val _isPlaying = MutableLiveData<Boolean>()
    var previousState = TrackList()

    fun getTrackList(): LiveData<TrackList> = _trackList


    fun isPlaying(): LiveData<Boolean> = _isPlaying


    fun loadAlbum(id: String) = launchOnUI {
        val list = musicService.getTracksListByAlbumId(id)
        _trackList.postValue(list)
    }

    fun changeList(list: TrackList) {
        _trackList.postValue(list)
        Log.d("ThangDN6 - PlayerViewModel", "changeList: ${list.pivot}")
    }


    fun changeState(isPlaying: Boolean) {
        _isPlaying.postValue(isPlaying)
    }

}