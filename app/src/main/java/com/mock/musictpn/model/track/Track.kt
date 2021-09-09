package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity
data class Track(
    @PrimaryKey
    val id : String,
    val name : String,
    val artistId:String,
    val albumId : String,
    val previewURL:String
): Serializable
