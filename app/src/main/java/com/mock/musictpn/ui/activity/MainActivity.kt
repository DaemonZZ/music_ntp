package com.mock.musictpn.ui.activity

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ActivityMainBinding
import com.mock.musictpn.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.mock.musictpn.app.service.MusicService
import com.mock.musictpn.viewmodel.PlayerViewModel
import com.mock.musictpn.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        const val RC_PERMISSION: Int = 100
        var mService: MusicService? = null

    }
    private var isFinishApp = false

    private val connection = object : ServiceConnection {
        private var isConnected = false
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isConnected = true
            Log.d("ThangDN6 - PlayerFragment", "onServiceConnected: ")
            val binder = service as MusicService.MusicBinder
            mService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("ThangDN6 - MainActivity", "onServiceDisconnected: ")
            isConnected = false
        }
        fun isConnected() = isConnected
    }
    override val mViewModel: MainViewModel by viewModels()
    val playerViewModel by viewModels<PlayerViewModel>()
    override fun getLayoutRes(): Int = R.layout.activity_main



    override fun setupViews() {
        checkPermission()
        val intent = Intent(this, MusicService::class.java)
        bindService(intent,connection, Context.BIND_AUTO_CREATE)


        mViewModel.getTopTracksTrending()
        mViewModel.getAlbumBanner()
        mViewModel.getGenres()
        mViewModel.getAlbums()

    }

    override fun setupListeners() {

    }

    override fun setupObservers() {
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RC_PERMISSION
            )
        } else {
            mViewModel.fetchTracksLocal()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_PERMISSION &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            mViewModel.fetchTracksLocal()
        } else {
            //you_have_not_enough_grant_permission
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + this.packageName)
            startActivityForResult(intent, RC_PERMISSION)
        }
    }


    override fun onBackPressed() {
        if (isFinishApp) {
            super.onBackPressed()
            return
        }
        isFinishApp = true
        Toast.makeText(this, getString(R.string.confirm_exit_app), Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            isFinishApp = false
        }, 2000)


    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }
}
