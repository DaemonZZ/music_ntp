package com.mock.musictpn.datasource

import androidx.lifecycle.LiveData
import com.mock.musictpn.datasource.local.TrackDataSource
import com.mock.musictpn.datasource.local.dao.FavoriteDao
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.genre.GenreList
import com.mock.musictpn.model.search.SearchResult
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import retrofit2.Response
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val source: TrackDataSource,
    private val apiService: IMusicService,
    private val favoriteDao: FavoriteDao
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

    suspend fun getTracksByAlbumId(albumId: String): Response<TrackList> {
        return apiService.getTracksByAlbumId(albumId)
    }

    suspend fun getTracksByGenreId(genreId: String): Response<TrackList> {
        return apiService.getTracksByGenreId(genreId)
    }

    suspend fun getGenres(): Response<GenreList> {
        return apiService.getGenreList()
    }

    suspend fun getAlbums(): Response<AlbumList> {
        return apiService.getTopTrendingAlbums(ApiContract.RANGE_MONTH, 20)
    }

    suspend fun searchByKeyword(name: String): Response<SearchResult>{
        return apiService.searchByKeyword(name)
    }

    suspend fun insertFavoriteTrack(track: Track): Long{
        return favoriteDao.insertTrack(track)
    }

    suspend fun deleteFavoriteTrack(track: Track): Int{
        return favoriteDao.deleteTrack(track)
    }

    fun getFavoriteTracks(): LiveData<List<Track>>{
        return favoriteDao.getFavoriteTracks()
    }

}