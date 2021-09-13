package com.mock.musictpn.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ActivityMainBinding
import com.mock.musictpn.ui.adapter.MainViewPagerAdapter
import com.mock.musictpn.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import android.media.MediaPlayer
import androidx.core.net.toUri


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        const val RC_PERMISSION: Int = 100
    }

    private val titleList = listOf("Home", "Trending", "Album", "Genre")
    private val iconList =
        listOf(R.drawable.ic_home, R.drawable.ic_trending, R.drawable.ic_album, R.drawable.ic_genre)

    override val mViewModel: MainViewModel by viewModels()
    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.vpMain.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(mBinding.tabMain, mBinding.vpMain) { tab, index ->
            tab.text = titleList[index]
            tab.setIcon(iconList[index])
        }.attach()
        mBinding.vpMain.isUserInputEnabled = false
    }

    override fun setupViews() {
        checkPermission()
        mViewModel.getTopTrendingTracks()
        mViewModel.getAlbums()
        mViewModel.getGenres()
    }

    override fun setupListeners() {
        mBinding.btnSearch.setOnClickListener {
            val mediaPlayer =
                MediaPlayer.create(this, "content://media/external/audio/media/6135".toUri())
            mediaPlayer.start()
        }
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
            Log.d("NganHV", "checkPermission: OK")
            //getData
//            getAllTrack()
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
            Log.d("NganHV", "checkPermission: OK")
            //getData
//            getAllTrack()
            mViewModel.fetchTracksLocal()
        } else {
            //you_have_not_enough_grant_permission
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + this.packageName)
            startActivityForResult(intent, RC_PERMISSION)
        }
    }
}
