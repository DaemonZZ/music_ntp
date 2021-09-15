package com.mock.musictpn.ui.fragment.player.player_inside

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentCurrentListBinding
import com.mock.musictpn.ui.adapter.CurrentPlaylistAdapter
import com.mock.musictpn.ui.adapter.listener.OnPlaylistItemClickedListener
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

            if(it!=null){
                var isListChanged = true
                val adapter = CurrentPlaylistAdapter(it.tracks)
                adapter.setOnTrendingClickedListener(object:OnPlaylistItemClickedListener{
                    override fun onTrackSelected(position: Int) {
                        val list = it
                        if (list.pivot != position){
                            list.pivot = position
                            isListChanged =true
                        }
                        else isListChanged = false
                        mViewModel.changeList(list)
                    }
                })
                if(isListChanged){
                    mBinding.rvCurrentList.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }

        })
    }

    override fun setupViews() {

    }
}