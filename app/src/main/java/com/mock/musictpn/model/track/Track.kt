package com.mock.musictpn.model.track

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.artist.Artist
import java.io.Serializable

@Entity
data class Track(
    @PrimaryKey
    val id: String,
    val name: String,
    val artistId: String,
    val albumId: String,
    val previewURL: String
) : Serializable

{
    suspend fun getArtist(apiService: IMusicService):Artist{
        return apiService.getArtistById(artistId).artists[0]
    }

    suspend fun getAlbum(apiService: IMusicService): Album {
        return apiService.getAlbumById(albumId).albums[0]
    }
}
