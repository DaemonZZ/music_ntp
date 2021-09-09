package com.mock.musictpn.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mock.musictpn.R
import com.mock.musictpn.network.ApiContract
import com.mock.musictpn.network.IMusicService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var scope: CoroutineScope
    @Inject
    lateinit var apiService: IMusicService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch {
            var tracks = apiService.getTrackById("tra.609492941")
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getTrackById: ${item.name} ")
            }

            tracks = apiService.getTopTracks()
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getTopTracks: ${item.name} ")
            }

            tracks = apiService.getTopTrendingTracks(ApiContract.RANGE_WEEK)
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getTopTrendingTracks: ${item.name} ")
            }

             tracks = apiService.getTracksListByAlbumId("alb.609492932")
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getTracksListByAlbumId: ${item.name} ")
            }

             tracks = apiService.getTrackListByGenreId("g.397")
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getTrackListByGenreId: ${item.name} ")
            }

             tracks = apiService.getPlaylistTracks("pp.225974698")
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getPlaylistTracks: ${item.name} ")
            }
            tracks = apiService.getTrackListByArtistId("art.28463069")
            for(item in tracks.tracks){
                Log.d("ThangDN6", "getTrackListByArtistId: ${item.name} ")
            }


            val albums = apiService.getTopTrendingAlbums(ApiContract.RANGE_WEEK)
            for(item in albums.albums){
                Log.d("ThangDN6", "getTopTrendingAlbums: ${item.name}")
            }

            var images = apiService.getAlbumImagesById("alb.609492932")
            for(item in images.images){
                Log.d("ThangDN6", "getAlbumImagesById: ${item.url}")
            }

            images = apiService.getArtistImagesById("art.28463069")
            for(item in images.images){
                Log.d("ThangDN6", "getAlbumImagesById: ${item.url}")
            }

        }

    }

}