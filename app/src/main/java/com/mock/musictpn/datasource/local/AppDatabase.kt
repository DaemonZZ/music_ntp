package com.mock.musictpn.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mock.musictpn.datasource.local.dao.FavoriteDao
import com.mock.musictpn.model.track.Track

@Database(entities = [Track::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}