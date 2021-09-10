package com.mock.musictpn.data.network

object ApiContract {
    const val BASE_URL = "http://api.napster.com/v2.2/"
    const val API_KEY = "MDgwMjZjZmUtMDcyMC00ZmRhLWE1ODktNDdhNDM0N2E1ZjY4"
    const val RANGE_DAY = "day"
    const val RANGE_WEEK = "week"
    const val RANGE_MONTH = "month"
    const val RANGE_YEAR = "year"
    const val RANGE_LIFE = "life"
}


/*
Full-Text Search

Returns an array of typed results by keyword or keyword combination.

    /v2.2/search/verbose?query=weezer blue album
    /v2.2/search/verbose?query=say it aint so&type=track&per_type_limit=1
    /v2.2/search/verbose?query=rap&type=playlist&per_type_limit=1&offset=1
    /v2.2/search/verbose?query=weezer&type=playlist&per_type_limit=5&playlist_type=editorial



   Top Tracks

Returns a list of the top tracks across Rhapsody, updated daily.

    /v2.2/tracks/top
    /v2.2/tracks/top?limit=5
    /v2.2/tracks/top?limit=5&offset=2

This endpoint supports optional query parameter range which can have one of values: day, week, month, year and life. Default value is month.

    /v2.2/tracks/top?range=week



 GET Track Detail

Returns detailed information about a track or tracks, including artist and album and genre information.

    /v2.2/tracks/tra.5156528

Note: For multiple tracks, just supply a comma separated list of track ids.

    /v2.2/tracks/tra.5156528,tra.5156528



@url track of playlist: "https://api.napster.com/v2.2/playlists/{id}/tracks"

@url artist images list	"https://api.napster.com/v2.2/artists/{id}/images"

@url album images list "https://api.napster.com/v2.2/albums/{id}/images"

@url album tracks list "https://api.napster.com/v2.2/albums/{id}/tracks"
 */