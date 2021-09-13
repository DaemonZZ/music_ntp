package com.mock.musictpn.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.genre.GenreList
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _tracksLocal = MutableLiveData<List<Track>>()
    val tracksLocal: LiveData<List<Track>> get() = _tracksLocal

    private val _trackList = MutableLiveData<TrackList>()
    val trackList: LiveData<TrackList> get() = _trackList

    private val _albumBanner = MutableLiveData<AlbumList>()
    val albumBanner: LiveData<AlbumList> get() = _albumBanner

    private val _genreList = MutableLiveData<GenreList>()
    val genreList: LiveData<GenreList> get() = _genreList

    private val _albums = MutableLiveData<AlbumList>()
    val albums: LiveData<AlbumList> get() = _albums

    fun fetchTracksLocal() = launchOnUI {
        val data = asyncOnIOAwait { trackRepository.fetchTracksLocal() }
        _tracksLocal.postValue(data)
    }

    fun getTopTracksTrending() = launchOnUI {
        val response = trackRepository.getTopTracksTrending()
        if (response.isSuccessful) {
            response.body()?.let {
                _trackList.postValue(it)
            }
        }
    }

    fun getAlbumBanner() = launchOnUI {
        val response = trackRepository.getAlbumBanner()
        if (response.isSuccessful) {
            response.body()?.let {
                _albumBanner.postValue(it)
            }
        }
    }

    fun getGenres() = launchOnUI {
        val response = trackRepository.getGenres()
        if (response.isSuccessful) {
            response.body()?.let {
                _genreList.postValue(it)
            }
        }
    }

    fun getAlbums() = launchOnUI {
        val response = trackRepository.getAlbums()
        if (response.isSuccessful) {
            response.body()?.let {
                _albums.postValue(it)
            }
        }
    }

}