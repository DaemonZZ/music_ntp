package com.mock.musictpn.model.playlist

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.mock.musictpn.model.track.TrackList
import java.io.Serializable

/**
 *  a list of many track
 *  @param imgUrl: image link
 */
class Playlist(
    val id : String,
    @SerializedName("url")
    val imgUrl:String,
)

