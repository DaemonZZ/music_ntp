package com.mock.musictpn.model.album

import androidx.room.Entity
import com.mock.musictpn.model.artist.Artist
import com.mock.musictpn.model.image.ImageList
import com.mock.musictpn.model.track.TrackList

class Album(
    val id : String,
    val name: String,
    val released: String
)