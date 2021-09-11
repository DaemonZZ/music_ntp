package com.mock.musictpn.model.playlist

import com.google.gson.annotations.SerializedName
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.track.TrackList

/**
 *  a list of many track
 *  @param imgUrl: image link
 */
class Playlist(
    val id: String,
    @SerializedName("url")
    val imgUrl: String,
) {
    suspend fun getTrackList(apiService: IMusicService):TrackList{
        return apiService.getPlaylistTracks(id)
    }
}

