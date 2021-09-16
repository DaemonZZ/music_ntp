package com.mock.musictpn.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mock.musictpn.model.track.Track

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track): Long

    @Delete
    suspend fun deleteTrack(track: Track): Int

    @Query("SELECT * FROM favorite_tracks")
    fun getFavoriteTracks(): LiveData<List<Track>>
}