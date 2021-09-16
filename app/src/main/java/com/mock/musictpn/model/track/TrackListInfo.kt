package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_list")
data class TrackListInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name : String? = null
) {
    fun getListName() = name ?: "Playlist $id"
}