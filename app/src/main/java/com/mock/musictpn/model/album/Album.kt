package com.mock.musictpn.model.album

import androidx.room.Entity
import com.mock.musictpn.model.artist.Artist
import com.mock.musictpn.model.image.ImageList
import com.mock.musictpn.model.track.TrackList

@Entity
class Album(
    val id : String,
    val name: String,
    val released: String
) {
    fun getImages(): ImageList {
        TODO("waiting for api")
    }
    fun getTracks(): TrackList {
        TODO("waiting for api")
    }
    fun getArtist():List<Artist>{
        TODO("waiting for api")
    }
}