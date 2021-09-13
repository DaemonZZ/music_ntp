package com.mock.musictpn.mediaplayer

interface OnPlayerStateChangedListener {
    fun onStateChange()
    fun onTrackChange()
    fun onStartedPlaying()
}