package com.mock.musictpn.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ActivityMainBinding
import com.mock.musictpn.ui.adapter.MainViewPagerAdapter
import com.mock.musictpn.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val titleList = listOf("Home","Trending","Album","Genre")
    private val iconList = listOf(R.drawable.ic_home,R.drawable.ic_trending,R.drawable.ic_album,R.drawable.ic_genre)

    override val mViewModel: MainViewModel by viewModels()
    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.vpMain.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(mBinding.tabMain,mBinding.vpMain){ tab, index ->
            tab.text = titleList[index]
            tab.setIcon(iconList[index])
        }.attach()
        mBinding.vpMain.isUserInputEnabled = false
    }

}