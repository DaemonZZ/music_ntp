package com.mock.musictpn.model.track

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrackList(
    @Relation(
        parentColumn = "id",
        entityColumn = "playListId"
    )
    @SerializedName("tracks")
    var tracks: List<Track> = listOf(),

    @Ignore
    var pivot:Int = 0,

    @Embedded
    var playListInfo: TrackListInfo? = null


) :Serializable
