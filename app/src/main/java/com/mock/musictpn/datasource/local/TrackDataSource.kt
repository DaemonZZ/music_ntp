package com.mock.musictpn.datasource.local

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import com.mock.musictpn.model.track.Track
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TrackDataSource @Inject constructor(private val application: Application) {
    companion object {
        const val ID = MediaStore.Audio.Media._ID
        const val TITLE = MediaStore.Audio.Media.TITLE
        const val ARTIST = MediaStore.Audio.Media.ARTIST
        const val ALBUM_ID = MediaStore.Audio.Media.ALBUM_ID
        const val ORDER_BY = MediaStore.Audio.Media.DATE_ADDED
    }

    fun fetchTracksLocal(): List<Track> {
        val tracks = ArrayList<Track>()
        val projection = arrayOf(ID, TITLE, ARTIST, ALBUM_ID)
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = application.contentResolver.query(uri, projection, null, null, "$ORDER_BY DESC")
        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getLong(0)
                val displayName = it.getString(1)
                val artist = it.getString(2)
                val albumId = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                val imageUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
                val trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                val track = Track(
                    previewURL = trackUri.toString(),
                    name = displayName,
                    artistName = artist,
                    imageLocal = imageUri.toString()
                )
                tracks.add(track)
            }
            it.close()
        }
        return tracks
    }
}