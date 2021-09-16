package com.mock.musictpn.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.model.track.TrackListInfo
import kotlinx.coroutines.selects.select

@Dao
interface PlayListDao {
    companion object{
        const val ID_LIST_FAVORITE = 1
        const val ID_LIST_HISTORY = 2
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackList(list:TrackListInfo): Long

    @Delete
    suspend fun deleteTrack(track: Track): Int

    @Query("delete from track where previewURL = :url and playListId = :playlist")
    suspend fun deleteByUrl(url:String,playlist:Int)

    /**
     * This function list all the old tracks
     * @param id: the nearest id you want to keep
     *
     */
    @Query("select * from track where localId < :id and playListId = 2")
    suspend fun filterHistory(id: Int) : List<Track>

    @Query("SELECT * FROM track")
    fun getFavoriteTracks(): LiveData<TrackList>

    @Query("SELECT * FROM track_list where id = :id")
    fun getListByIDLiveData(id:Int) : LiveData<TrackList>

    @Query("SELECT * FROM track_list where id = :id")
    fun getListByID(id:Int) : TrackList




}