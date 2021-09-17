package com.mock.musictpn.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        const val CONTENT_LOCAL = "content://"

    }

    private var player: MediaPlayer = MediaPlayer()

    private lateinit var stateChangedListener: OnPlayerStateChangedListener
    var listTrack: TrackList = TrackList()
    private var isShuffle = true
    private var repeatMode = MODE_REPEAT_WHOLE_LIST
    private var isStopped = true
    private var pausePosition = 0
    private lateinit var context: Context

    init {
        player.setOnCompletionListener {
            Log.d("ThangDN6 - MusicPlayer", "reached event: Complete track")
            if (isShuffle && repeatMode != MODE_REPEAT_ONE_TRACK) next()
            else
                when (repeatMode) {
                    MODE_NO_REPEAT -> if (listTrack.pivot == listTrack.tracks.size - 1) stop() else next()
                    MODE_REPEAT_ONE_TRACK -> playTrack(listTrack.pivot)
                    MODE_REPEAT_WHOLE_LIST -> if (listTrack.pivot == listTrack.tracks.size - 1) playTrack(
                        0
                    ) else next()
                }
        }
        player.setOnErrorListener { _, what, extra ->
            Log.d("ThangDN6 - MusicPlayer", "$what: $extra ")
            false
        }
        player.setOnPreparedListener {
            Log.d("ThangDN6 - MusicPlayer", ": onPrepared")
            it.start()
            isStopped = false
            stateChangedListener.onStartedPlaying()
            stateChangedListener.onStateChange()
        }
    }


    fun playTrack(index: Int) {

        if (listTrack.pivot != index) {
            stateChangedListener.onTrackChange()
            listTrack.pivot = index
        }
        val track = listTrack.tracks[index]
        player.reset()
        if(track.previewURL.contains(CONTENT_LOCAL)){
            player.setDataSource(context,track.previewURL.toUri())
        }
        else {
            player.setDataSource(track.previewURL)
        }

        player.prepareAsync()
        //player.start()

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
        if(isStopped){
            playTrack(getCurrentIndex())
            isStopped = false
        }
        stateChangedListener.onStateChange()
    }

    fun next() {
        if (listTrack.tracks.isNotEmpty()) {
            if (isShuffle) {
                val index = listTrack.tracks.indices.random()
                playTrack(index)
            } else if (listTrack.pivot == listTrack.tracks.size - 1) {
                playTrack(0)
            } else {
                playTrack(listTrack.pivot + 1)
            }
        }

    }

    fun prev() {
        if (listTrack.tracks.isNotEmpty()) {
            if (isShuffle) {
                val index = listTrack.tracks.indices.random()
                playTrack(index)
            } else if (listTrack.pivot == 0) {
                playTrack(listTrack.tracks.size - 1)
            } else {
                playTrack(listTrack.pivot - 1)
            }
        }

    }

    fun toggleShuffle() {
        isShuffle = !isShuffle
        Log.d("ThangDN6 - MusicPlayer", "toggleShuffle: isShuffle = $isShuffle")
        stateChangedListener.onStateChange()
    }

    fun toggleRepeat() {
        if (repeatMode == MODE_REPEAT_WHOLE_LIST) {
            repeatMode = MODE_NO_REPEAT
        } else {
            repeatMode++
        }
        stateChangedListener.onStateChange()
        Log.d("ThangDN6 - MusicPlayer", "toggleRepeat: ")
    }

    fun stop() {
        player.stop()
       // player.release()
        isStopped = true
        stateChangedListener.onStateChange()
    }

    fun getCurrentTrack(): Track {
        return listTrack.tracks[listTrack.pivot]
    }

    fun getCurrentIndex() = listTrack.pivot

    fun setOnPlayerStateChangedListener(listener: OnPlayerStateChangedListener) {
        this.stateChangedListener = listener
    }

    fun isShuffle() = isShuffle
    fun getRepeatMode() = repeatMode
    fun isPlaying() = try { player.isPlaying } catch (e:IllegalStateException){ false }
    fun isStopped() = isStopped
    fun getTrackDuration() = player.duration
    fun getCurrentPosition() = try { player.currentPosition } catch (e:IllegalStateException){ 0 }
    fun seekTo(position: Int) = player.seekTo(position)

    fun setOnErrorListener(listener: MediaPlayer.OnErrorListener) {
        this.player.setOnErrorListener(listener)
    }
    fun setContext(context: Context){
        this.context = context
    }
}
