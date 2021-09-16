package com.mock.musictpn.ui.adapter.listener

import com.mock.musictpn.model.track.TrackList

interface OnTrackItemClickedListener {
    fun onClick(tracks: TrackList)
}