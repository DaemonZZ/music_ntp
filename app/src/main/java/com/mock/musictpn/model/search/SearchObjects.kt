package com.mock.musictpn.model.search

import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.artist.Artist
import com.mock.musictpn.model.track.Track

class SearchObjects(
    val albums: List<Album>,
    val artists: List<Artist>,
    val tracks: List<Track>
)