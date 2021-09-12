package com.mock.musictpn.model.genre

import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.track.TrackList

class Genre(
    val id: String,
    val name: String,
    val description: String
) {
    fun getImageUrl(): String = "https://api.napster.com/imageserver/images/$id/240x160.jpg"

    suspend fun getListTrack(apiService: IMusicService): TrackList {
        return apiService.getTrackListByGenreId(id)
    }
}