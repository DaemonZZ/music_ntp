package com.mock.musictpn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mock.musictpn.data.local.dao.TrackDao
import com.mock.musictpn.model.track.Track

@Database(entities = [Track::class], version = 1)
abstract class FavoriteTrackDatabase : RoomDatabase() {
    abstract fun getDao(): TrackDao
}