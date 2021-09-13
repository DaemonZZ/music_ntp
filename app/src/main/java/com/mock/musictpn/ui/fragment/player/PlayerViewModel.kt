package com.mock.musictpn.ui.fragment.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : BaseViewModel() {

    private val _trackList = MutableLiveData<TrackList>()  //Current list
    private val _currentTrack = MutableLiveData<Track>()
    private val _isPlaying = MutableLiveData<Boolean>()

    fun getTrackList():LiveData<TrackList> =  _trackList

    fun getCurrentTrack():LiveData<Track> = _currentTrack

    fun isPlaying():LiveData<Boolean> = _isPlaying


    suspend fun loadAlbum(id:String){
        val list = musicService.getTracksListByAlbumId(id)
        _trackList.postValue(list)
    }

    fun loadCurrent(track:Track){
        _currentTrack.postValue(track)
    }

    fun changeState(isPlaying:Boolean){
        _isPlaying.postValue(isPlaying)
    }

}