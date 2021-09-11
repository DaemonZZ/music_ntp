package com.mock.musictpn.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mock.musictpn.ui.fragment.album.AlbumFragment
import com.mock.musictpn.ui.fragment.genre.GenreFragment
import com.mock.musictpn.ui.fragment.home.HomeFragment
import com.mock.musictpn.ui.fragment.trending.TrendingFragment

class MainViewPagerAdapter(activity:AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> HomeFragment()
            1 -> TrendingFragment()
            2 -> AlbumFragment()
            3-> GenreFragment()
            else -> throw  NotImplementedError("Not implement fragment")
        }
    }
}