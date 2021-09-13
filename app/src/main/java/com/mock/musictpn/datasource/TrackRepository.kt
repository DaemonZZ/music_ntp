package com.mock.musictpn.datasource

import com.mock.musictpn.datasource.local.TrackDataSource
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.genre.GenreList
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import retrofit2.Response
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val source: TrackDataSource,
    private val apiService: IMusicService
) {
    fun fetchTracksLocal(): List<Track> {
        return source.fetchTracksLocal()
    }

    suspend fun getAlbumBanner(): Response<AlbumList> {
        return apiService.getTopTrendingAlbums(ApiContract.RANGE_WEEK, 5)
    }

    suspend fun getTopTracksTrending(): Response<TrackList> {
        return apiService.getTopTracksTrending(ApiContract.RANGE_YEAR, 50)
    }

    suspend fun getGenres(): Response<GenreList> {
        return apiService.getGenreList()
    }

    suspend fun getAlbums(): Response<AlbumList> {
        return apiService.getTopTrendingAlbums(ApiContract.RANGE_MONTH, 20)
    }

}