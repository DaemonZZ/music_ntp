package com.mock.musictpn.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.mock.musictpn.utils.Const.CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MusicApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    private fun createChannel() {
        val notificationChannel =  NotificationChannel(CHANNEL_ID,"Music Application channel",
            NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.setSound(null,null)
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(notificationChannel)
    }
}