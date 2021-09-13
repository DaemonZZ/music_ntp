package com.mock.musictpn.model.album

import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.track.TrackList
import java.io.Serializable

data class Album(
    val id: String,
    val name: String,
    val released: String,
    val artistName: String
){
    /**
     * Album Image size
     * 70x70-200x200-300x300-500x500
     *
     */
    fun getImageUrl(): String = "https://api.napster.com/imageserver/v2/albums/$id/images/170x170.jpg"
}