package com.mock.musictpn.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mock.musictpn.mediaplayer.MusicPlayer
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MusicReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var musicController:MusicPlayer

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ThangDN6 - MusicReceiver", "onReceive: ")
       when(intent.action){
           MusicPlayer.ACTION_PLAY -> {
               Log.d("ThangDN6 - MusicReceiver", "onReceive: play")
               musicController.togglePlayButton()
           }
           MusicPlayer.ACTION_PAUSE -> {
               Log.d("ThangDN6 - MusicReceiver", "onReceive: pause")
               musicController.togglePlayButton()
           }
           MusicPlayer.ACTION_PREV -> {
               Log.d("ThangDN6 - MusicReceiver", "onReceive: prev")
               musicController.prev()
           }
           MusicPlayer.ACTION_NEXT -> {
               Log.d("ThangDN6 - MusicReceiver", "onReceive: next")
               musicController.next()
           }
           MusicPlayer.ACTION_STOP -> {
               Log.d("ThangDN6 - MusicReceiver", "onReceive: stop")
               musicController.stop()
           }
           else -> throw Exception("Unknown action")
       }
    }
}