package com.mock.musictpn.ui.fragment.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.genre.GenreList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor() : BaseViewModel() {
    private val _genreList = MutableLiveData<GenreList>()
    val genreList: LiveData<GenreList> get() = _genreList

    fun getGenres() = launchOnUI {
        val response = musicService.getGenreList()
        if (response.isSuccessful) {
            response.body()?.let {
                _genreList.postValue(it)
            }
        }

    }
}