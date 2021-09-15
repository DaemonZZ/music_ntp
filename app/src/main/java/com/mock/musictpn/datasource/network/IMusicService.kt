package com.mock.musictpn.datasource.network

import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.image.ImageList
import com.mock.musictpn.model.search.SearchResult
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.datasource.network.ApiContract.API_KEY
import com.mock.musictpn.model.artist.ArtistList
import com.mock.musictpn.model.genre.GenreList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The interface contain all function which help application get data from API
 * @author ThangDN6
 *
 */
interface IMusicService {

    /**
     * Get music track by id
     * @param id : track id
     */
    @GET("tracks/{id}")
    suspend fun getTrackById(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): TrackList

    /**
     * Get artist by id
     * @param id artist id
     */
    @GET("artists/{id}")
    suspend fun getArtistById(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ArtistList

    /**
     * Get albums by id
     * @param id artist id
     */
    @GET("albums/{id}")
    suspend fun getAlbumById(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): AlbumList


    /**
     * Get all tracks of a album which have id = {id}
     * @param id: Album id
     */
    @GET("albums/{id}/tracks")
    suspend fun getTracksByAlbumId(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): Response<TrackList>

    /**
     * Get all tracks of a genre which have id = {id}
     * @param id: Genre id
     */
    @GET("genres/{id}/tracks/top")
    suspend fun getTracksByGenreId(
        @Path("id") id: String,
        @Query("limit") limit: Int = 10,
        @Query("apikey") apiKey: String = API_KEY
    ): Response<TrackList>

    @GET("artists/{id}/tracks")
    suspend fun getTrackListByArtistId(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): TrackList


    /**
     * Get Image album by id
     * @param id: Album Id
     * @return List images with multiple sizes
     */
    @GET("albums/{id}/images")
    suspend fun getAlbumImagesById(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ImageList

    /**
     * Get list Images of artist with multiple sizes
     * @param id: artist id
     * @return list images with multiple sizes
     */
    @GET("artists/{id}/images")
    suspend fun getArtistImagesById(
        @Path("id") id: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ImageList


    /**
     * Get All tracks of playlist
     * @param id: playlist id
     * @return ListTracks of playlist
     */
    @GET("playlists/{id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("id") id: String,
        @Query("limit") limit: Int = 20,
        @Query("apikey") apiKey: String = API_KEY
    ): TrackList


    /**
     * Search everything by keyword (tracks, artists, albums)
     * @param keyword: the keyword to search
     * @param limit: max count of response results each type
     */
    @GET("search")
    suspend fun searchByKeyword(
        @Query("query") keyword: String = "",
        @Query("per_type_limit") limit: Int = 10,
        @Query("apikey") apiKey: String = API_KEY
    ): SearchResult


    /**
     * Get top music tracks for displaying on main screen slider
     * @param limit: max count of response results
     */
    @GET("tracks/top")
    suspend fun getTopTracks(
        @Query("limit") limit: Int = 5,
        @Query("apikey") apiKey: String = API_KEY
    ): Response<TrackList>

    /**
     * Get top trending tracks in a range
     * @param range the time scope of trending results,
     *              it must be one of ApiContract.RANGE_DAY,ApiContract.RANGE_WEEK,ApiContract.RANGE_MONTH,ApiContract.RANGE_YEAR or ApiContract.RANGE_LIFE
     *
     * @param limit max count of response results each type
     */
    @GET("tracks/top")
    suspend fun getTopTracksTrending(
        @Query("range") range: String,
        @Query("limit") limit: Int = 10,
        @Query("apikey") apiKey: String = API_KEY
    ): Response<TrackList>

    /**
     * Get top trending albums in a range
     * @param range the time scope of trending results,
     *              it must be one of ApiContract.RANGE_DAY,ApiContract.RANGE_WEEK,ApiContract.RANGE_MONTH,ApiContract.RANGE_YEAR or ApiContract.RANGE_LIFE
     * @param limit max count of response results each type
     */
    @GET("albums/new")
    suspend fun getTopTrendingAlbums(
        @Query("range") range: String,
        @Query("limit") limit: Int = 10,
        @Query("apikey") apiKey: String = API_KEY
    ): Response<AlbumList>

    /**
     * Get all genres
     */
    @GET("genres")
    suspend fun getGenreList(
        @Query("apikey") apiKey: String = API_KEY
    ): Response<GenreList>

}