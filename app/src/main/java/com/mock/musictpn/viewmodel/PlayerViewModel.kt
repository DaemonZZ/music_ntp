package com.mock.musictpn.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.datasource.local.dao.PlayListDao
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : BaseViewModel() {
    private val _trackList = MutableLiveData<TrackList>()  //Current list
    private val _isPlaying = MutableLiveData<Boolean>()
    var previousState = TrackList()

    fun getTrackList(): LiveData<TrackList> = _trackList


    fun isPlaying(): LiveData<Boolean> = _isPlaying


    fun changeList(list: TrackList) {
        if(list != previousState){
            previousState = TrackList()
        }
        _trackList.postValue(list)
        Log.d("ThangDN6 - PlayerViewModel", "changeList: ${list.tracks[list.pivot].name}")
    }


    fun changeState(isPlaying: Boolean) {
        _isPlaying.postValue(isPlaying)
    }


    fun insertFavoriteTrack(track: Track) = launchOnUI {
        asyncOnIOAwait { trackRepository.insertTrack(track, PlayListDao.ID_LIST_FAVORITE) }
    }

    fun deleteFavoriteTrack(track: Track) = launchOnUI {
        asyncOnIOAwait { trackRepository.deleteTrack(track) }
    }

    fun getFavoriteTracks(): LiveData<TrackList> = trackRepository.getFavoriteTracks()


    fun getHistoryTracks(): TrackList = trackRepository.getHistoryTracks()

    suspend fun insertHistoryTrack(track: Track, isExist: Boolean) {
        if (isExist) {
            trackRepository.deleteByUrl(track.previewURL,PlayListDao.ID_LIST_HISTORY)
        }
        trackRepository.insertTrack(track, PlayListDao.ID_LIST_HISTORY)
    }

    suspend fun insertHistoryTrack(track: Track, oldestId: Int) {
        Log.d("ThangDN6 - PlayerViewModel", "insertHistoryTrack: $oldestId")
        insertHistoryTrack(track, false)
        if(oldestId!=0){
            val oldItems = trackRepository.getOlderTracks(oldestId)
            for (item in oldItems){
                trackRepository.deleteByUrl(item.previewURL,PlayListDao.ID_LIST_HISTORY)
            }
        }

    }


}