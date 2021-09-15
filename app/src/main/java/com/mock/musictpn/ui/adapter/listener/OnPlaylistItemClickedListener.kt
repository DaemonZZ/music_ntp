package com.mock.musictpn.ui.adapter.listener

import com.mock.musictpn.model.track.Track

interface OnPlaylistItemClickedListener {
    fun onTrackSelected(position: Int)
}