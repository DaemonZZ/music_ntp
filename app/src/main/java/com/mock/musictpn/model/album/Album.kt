package com.mock.musictpn.model.album

import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.image.ImageList
import com.mock.musictpn.model.track.TrackList

data class Album(
    val id: String,
    val name: String,
    val released: String
) {
    suspend fun getImages(apiService:IMusicService):ImageList{
        return apiService.getAlbumImagesById(id)
    }

    suspend fun getListTrack(apiService: IMusicService):TrackList{
        return apiService.getTracksListByAlbumId(id)
    }

}