package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "favorite_tracks")
data class Track(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val id: String? = "",
    val name: String = "",
    val artistId: String? = "",
    val albumId: String? = "",
    val artistName: String = "",
    val previewURL: String = "",
    val imageLocal: String? = ""
) : Serializable {
    /**
     * Track = Album
     * 70x70-200x200-300x300-500x500
     *
     */
    fun getImageUrl(): String {
        return if (albumId != null) {
            "https://api.napster.com/imageserver/v2/albums/$albumId/images/300x300.jpg"
        } else imageLocal!!
    }


}
