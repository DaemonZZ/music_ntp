package com.mock.musictpn.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mock.musictpn.ui.fragment.player.player_inside.ChangePageActionListener
import com.mock.musictpn.ui.fragment.player.player_inside.DiscFragment
import com.mock.musictpn.ui.fragment.player.player_inside.PlayListFragment

class DiscPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private lateinit var listener: ChangePageActionListener

    fun setChangePageActionListener(listener: ChangePageActionListener){
        this.listener = listener
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiscFragment().apply {
                setChangePageActionListener(this@DiscPagerAdapter.listener)
            }
            1 -> PlayListFragment().apply {
                setChangePageActionListener(this@DiscPagerAdapter.listener)
            }
            else -> throw NoSuchElementException("NoSuchElementException")
        }
    }
}