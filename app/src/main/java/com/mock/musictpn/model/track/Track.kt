package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "favorite_track")
data class Track(
    @PrimaryKey(autoGenerate = true)
    @Expose
    val localId: Int = 0,
    val id: String = "",
    val name: String = "",
    val artistId: String = "",
    val albumId: String? = null,
    val artistName: String = "Unknown",
    val previewURL: String,
    val isFavorite: Boolean = false,
    val imageLocal: String = ""
) : Serializable {
    /**
     * Track = Album
     * 70x70-200x200-300x300-500x500
     *
     */
    fun getImageUrl(): String? {
        return if (albumId != null) {
            "https://api.napster.com/imageserver/v2/albums/$albumId/images/170x170.jpg"
        } else null
    }


}
