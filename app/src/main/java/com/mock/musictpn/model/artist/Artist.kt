package com.mock.musictpn.model.artist

import androidx.room.Entity
import com.mock.musictpn.model.image.ImageList

@Entity
class Artist (
    val id:String,
    val name:String,
){
    fun getArtistImage(): ImageList {
        TODO("wait for api function")
    }
}