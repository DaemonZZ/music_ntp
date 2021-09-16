package com.mock.musictpn.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mock.musictpn.datasource.local.dao.PlayListDao
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackListInfo

@Database(entities = [Track::class , TrackListInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao():PlayListDao
}