package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mock.musictpn.datasource.network.IMusicService
import java.io.Serializable

@Entity
data class Track(
    @PrimaryKey
    val id: String,
    val name: String,
    val artistId: String,
    val albumId: String,
    val previewURL: String
) : Serializable {
    //Test
    fun getImageUrl():String{
        return "https://api.napster.com/imageserver/v2/albums/$albumId/images/70x70.jpg"
    }
}
