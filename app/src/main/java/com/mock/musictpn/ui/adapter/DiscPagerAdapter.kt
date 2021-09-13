package com.mock.musictpn.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mock.musictpn.ui.fragment.player.player_inside.DiscFragment
import com.mock.musictpn.ui.fragment.player.player_inside.PlayListFragment

class DiscPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiscFragment()
            1 -> PlayListFragment()
            else -> throw NoSuchElementException("NoSuchElementException")
        }
    }
}