package com.mock.musictpn.ui.fragment.player.player_inside

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentCurrentListBinding
import com.mock.musictpn.ui.adapter.CurentPlaylistAdapter
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.ui.fragment.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayListFragment : BaseFragment<FragmentCurrentListBinding, PlayerViewModel>() {

    override val mViewModel: PlayerViewModel by activityViewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_current_list
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        mViewModel.getTrackList().observe(this, {
            Log.d("ThangDN6 - PlayListFragment", "setupObservers: ")
            mBinding.rvCurrentList.apply {
                adapter = CurentPlaylistAdapter(it.tracks)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            }
        })
    }

    override fun setupViews() {

    }
}