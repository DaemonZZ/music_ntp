package com.mock.musictpn.model.playlist

import com.google.gson.annotations.SerializedName

/**
 *  a list of many track
 *  @param imgUrl: image link
 */
class Playlist(
    val id: String,
    @SerializedName("url")
    val imgUrl: String,
)

