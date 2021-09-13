package com.mock.musictpn.model.artist

import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.track.TrackList

class Artist(val id: String, val name: String){

    suspend fun getTrackList(apiService: IMusicService):TrackList{
        return apiService.getTrackListByArtistId(id)
    }
}