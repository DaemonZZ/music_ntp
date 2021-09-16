package com.mock.musictpn.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
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


    fun changeList(list: TrackList) {
        _trackList.postValue(list)
        Log.d("ThangDN6 - PlayerViewModel", "changeList: ${list.pivot}")
    }


    fun changeState(isPlaying: Boolean) {
        _isPlaying.postValue(isPlaying)
    }

    fun insertFavoriteTrack(track: Track) = launchOnUI {
        asyncOnIOAwait { trackRepository.insertFavoriteTrack(track) }
    }

    fun deleteFavoriteTrack(track: Track) = launchOnUI {
        asyncOnIOAwait { trackRepository.deleteFavoriteTrack(track) }
    }

    fun getFavoriteTracks(): LiveData<List<Track>> = trackRepository.getFavoriteTracks()
}