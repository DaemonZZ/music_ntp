package com.mock.musictpn.model.track

import com.google.gson.annotations.SerializedName

data class TrackList(
    @SerializedName("tracks")
    val tracks: List<Track> = listOf()
)
