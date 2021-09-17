package com.mock.musictpn.model.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity
data class Track(
    @PrimaryKey(autoGenerate = true)
    var localId: Int = 0,
    val id: String? = "",
    val name: String = "",
    val artistId: String? = "",
    val albumId: String? = null,
    val artistName: String = "",
    val previewURL: String = "",
    val imageLocal: String? = null,
    @ColumnInfo(index = true)
    var playListId : Int = 0
) : Serializable {
    /**
     * Track = Album
     * 70x70-200x200-300x300-500x500
     *
     */
    fun getImageUrl(): String? {
        return if (albumId != null) {
            "https://api.napster.com/imageserver/v2/albums/$albumId/images/300x300.jpg"
        } else imageLocal
    }


}
