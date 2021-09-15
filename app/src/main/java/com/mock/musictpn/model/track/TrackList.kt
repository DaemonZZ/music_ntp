package com.mock.musictpn.model.track

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrackList(
    @SerializedName("tracks")
    val tracks: List<Track> = listOf(),
    var pivot:Int = 0
) :Serializable
