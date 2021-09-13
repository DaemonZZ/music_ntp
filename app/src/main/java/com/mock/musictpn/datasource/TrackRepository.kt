package com.mock.musictpn.datasource

import com.mock.musictpn.datasource.local.TrackDataSource
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.track.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val source: TrackDataSource,
    private val apiService: IMusicService
) {

    suspend fun fetchAllTrack(): List<Track> {
        return withContext(Dispatchers.IO) {
            source.fetchAllTrack()
        }
    }
    suspend fun getAlbums(): Response<AlbumList>{
        return apiService.getTopTrendingAlbums(ApiContract.RANGE_WEEK, 5)
    }


}