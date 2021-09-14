package com.mock.musictpn.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ActivityMainBinding
import com.mock.musictpn.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import android.os.Handler
import android.os.Looper
import android.widget.Toast


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        const val RC_PERMISSION: Int = 100
    }

    private var isFinishApp = false

    override val mViewModel: MainViewModel by viewModels()
    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun setupViews() {
        checkPermission()
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
}
