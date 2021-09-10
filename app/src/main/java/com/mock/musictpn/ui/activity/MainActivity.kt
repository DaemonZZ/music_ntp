package com.mock.musictpn.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ActivityMainBinding
import com.mock.musictpn.ui.base.BaseActivity
import com.mock.musictpn.ui.fragment.album.AlbumFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val mViewModel: MainViewModel by viewModels()
    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, AlbumFragment())
            }
        }
    }


}