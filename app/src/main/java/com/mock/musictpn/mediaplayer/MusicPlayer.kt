package com.mock.musictpn.mediaplayer

import android.media.MediaPlayer
import android.util.Log
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList


interface OnPlayerStateChangedListener {
    fun onStateChange()
    fun onTrackChange()
}


/**
 * this class contained all function to handle media controller event
 * @author ThangDN6
 */
class MusicPlayer {
    companion object {
        const val MODE_NO_REPEAT = 0
        const val MODE_REPEAT_ONE_TRACK = 1
        const val MODE_REPEAT_WHOLE_LIST = 2

        const val ACTION_CHANGE_LIST = "change list"
        const val ACTION_START = "start"
        const val ACTION_STOP = "stop"
        const val ACTION_PLAY = "play"
        const val ACTION_PAUSE = "pause"
        const val ACTION_NEXT = "next"
        const val ACTION_PREV = "prev"
        const val ACTION_SHUFFLE = "shuffle"
        const val ACTION_REPEAT = "repeat"

    }

    private val player: MediaPlayer = MediaPlayer()
    private lateinit var listener: OnPlayerStateChangedListener
    var listTrack: TrackList = TrackList()
    private var currentTrack = 0
    private var isShuffle = true
    private var repeatMode = MODE_REPEAT_ONE_TRACK
    private var pausePosition = 0

    init {
        player.setOnCompletionListener {
            Log.d("ThangDN6 - MusicPlayer", "reached event: ")
            if (isShuffle && repeatMode != MODE_REPEAT_ONE_TRACK) next()
            else
                when (repeatMode) {
                    MODE_NO_REPEAT -> if (currentTrack == listTrack.tracks.size - 1) stop() else next()
                    MODE_REPEAT_ONE_TRACK -> playTrack(currentTrack)
                    MODE_REPEAT_WHOLE_LIST -> if (currentTrack == listTrack.tracks.size - 1) playTrack(
                        0
                    ) else next()
                }
        }
    }




    fun playTrack(index: Int) {
        currentTrack = index
        val track = listTrack.tracks[index]
        listener.onTrackChange()
        player.reset()
        player.setDataSource(track.previewURL)
        player.prepare()
        player.start()
        listener.onStateChange()
        Log.d("ThangDN6 - MusicPlayer", "playTrack: ${track.name}")
    }

    fun togglePlayButton() {
        if (player.isPlaying) {
            player.pause()
            pausePosition = player.currentPosition
            Log.d("ThangDN6 - MusicPlayer", "togglePlayButton: Player Paused")
        } else {
            player.seekTo(pausePosition)
            player.start()
            Log.d("ThangDN6 - MusicPlayer", "togglePlayButton: Resumed")
        }
        listener.onStateChange()
    }

    fun next() {
        if (isShuffle) {
            val index = listTrack.tracks.indices.random()
            playTrack(index)
        } else if (currentTrack == listTrack.tracks.size - 1) {
            playTrack(0)
        } else {
            playTrack(currentTrack + 1)
        }
    }

    fun prev() {
        if (isShuffle) {
            val index = listTrack.tracks.indices.random()
            playTrack(index)
        } else if (currentTrack == 0) {
            playTrack(listTrack.tracks.size - 1)
        } else {
            playTrack(currentTrack - 1)
        }
    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
        Log.d("ThangDN6 - MusicPlayer", "toggleShuffle: isShuffle = $isShuffle")
        listener.onStateChange()
    }

    fun toggleRepeat() {
        if (repeatMode == MODE_REPEAT_WHOLE_LIST) {
            repeatMode = MODE_NO_REPEAT
        } else {
            repeatMode++
        }
        listener.onStateChange()
        Log.d("ThangDN6 - MusicPlayer", "toggleRepeat: ")
    }

    fun stop() {
        player.stop()
        player.release()
        listener.onStateChange()
    }

    fun getCurrentTrack(): Track {
        return listTrack.tracks[currentTrack]
    }
    fun getcurrentIndex() = currentTrack

    fun setOnPlayerStateChangedListener(listener: OnPlayerStateChangedListener) {
        this.listener = listener
    }

    fun isShuffle() = isShuffle
    fun getRepeatMode() = repeatMode
    fun isPlaying() = player.isPlaying
    fun getTrackDuration() = player.duration
    fun getCurrentPosition() = player.currentPosition



}
