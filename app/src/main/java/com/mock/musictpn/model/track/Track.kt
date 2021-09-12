package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.artist.Artist
import java.io.Serializable

@Entity
data class Track(
    @PrimaryKey
    val id: String,
    val name: String,
    val artistId: String,
    val albumId: String,
    val artistName: String,
    val previewURL: String
) : Serializable {
    /**
     * Track = Album
     * 70x70-200x200-300x300-500x500
     *
     */
    fun getImageUrl(): String = "https://api.napster.com/imageserver/v2/albums/$albumId/images/170x170.jpg"

}
