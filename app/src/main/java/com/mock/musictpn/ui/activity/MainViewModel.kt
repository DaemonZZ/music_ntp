package com.mock.musictpn.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.genre.GenreList
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _tracksLocal = MutableLiveData<List<Track>>()
    val tracksLocal: LiveData<List<Track>> get() = _tracksLocal

    private val _trackList = MutableLiveData<TrackList>()
    val trackList: LiveData<TrackList> get() = _trackList

    private val _albumList = MutableLiveData<AlbumList>()
    val albumList: LiveData<AlbumList> get() = _albumList

    private val _genreList = MutableLiveData<GenreList>()
    val genreList: LiveData<GenreList> get() = _genreList

    fun fetchTracksLocal() = launchOnUI {
        val data = trackRepository.fetchAllTrack()
        _tracksLocal.postValue(data)
    }

    fun getTopTrendingTracks() = launchOnUI {
        val response = musicService.getTopTrendingTracks(ApiContract.RANGE_YEAR, 50)
        if (response.isSuccessful) {
            response.body()?.let {
                _trackList.postValue(it)
            }
        }
    }

    fun getAlbums() = launchOnUI {
        val response = trackRepository.getAlbums()
        if (response.isSuccessful) {
            response.body()?.let {
                _albumList.postValue(it)
            }
        }
    }

    fun getGenres() = launchOnUI {
        val response = musicService.getGenreList()
        if (response.isSuccessful) {
            response.body()?.let {
                _genreList.postValue(it)
            }
        }

    }


}