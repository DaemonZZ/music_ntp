package com.mock.musictpn.model.genre

import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.track.TrackList

class Genre (
    val id : String,
    val name:String,
    val description : String
        ){
    suspend fun getListTrack(apiService: IMusicService): TrackList {
        return apiService.getTrackListByGenreId(id)
    }
}