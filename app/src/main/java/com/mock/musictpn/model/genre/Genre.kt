package com.mock.musictpn.model.genre

import java.io.Serializable

class Genre(
    val id: String,
    val name: String,
    val description: String
) : Serializable {
    fun getImageUrl(): String = "https://api.napster.com/imageserver/images/$id/240x160.jpg"
}