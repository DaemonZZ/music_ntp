package com.mock.musictpn.model.playlist

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.mock.musictpn.model.track.TrackList
import java.io.Serializable

/**
 *  a list of many track
 *  @param imgUrl: image link
 */
@Entity
class Playlist(
    val id : String,
    @SerializedName("url")
    val imgUrl:String,
) : Serializable
{
    /**
     * get all track of playlist
     * @return Track list contained
     */
    fun getTracks(): TrackList {
        TODO("Wait for api function")
    }
}

