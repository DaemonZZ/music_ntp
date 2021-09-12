package com.mock.musictpn.ui.fragment.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor() : BaseViewModel() {
    private val _trackList = MutableLiveData<TrackList>()
    val trackList: LiveData<TrackList> get() = _trackList

    fun getTracks() = launchOnUI {
        val response = musicService.getTopTrendingTracks(ApiContract.RANGE_YEAR, 50)
        if (response.isSuccessful) {
            response.body()?.let {
                _trackList.postValue(it)
            }
        }
    }
}