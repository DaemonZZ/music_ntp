package com.mock.musictpn.model.album

import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.track.TrackList

data class Album(
    val id: String,
    val name: String,
    val released: String
) {
    /**
     * @param size Album Image
     * 70x70-200x200-300x300-500x500
     *
     */
    fun getImage(): String {
        return "https://api.napster.com/imageserver/v2/albums/$id/images/170x170.jpg"
    }

    suspend fun getListTrack(apiService: IMusicService): TrackList {
        return apiService.getTracksListByAlbumId(id)
    }

}