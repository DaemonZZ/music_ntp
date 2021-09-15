package com.mock.musictpn.ui.fragment.player.player_inside

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentCurrentListBinding
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.adapter.CurrentPlaylistAdapter
import com.mock.musictpn.ui.adapter.listener.OnPlaylistItemClickedListener
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayListFragment : BaseFragment<FragmentCurrentListBinding, PlayerViewModel>() {

    override val mViewModel: PlayerViewModel by activityViewModels()
    private lateinit var listener: ChangePageActionListener
    private lateinit var currentList: TrackList
    private var isFirstInit = true
    private val adapter = CurrentPlaylistAdapter()
    override fun getLayoutRes(): Int {
        return R.layout.fragment_current_list
    }

    fun setChangePageActionListener(listener: ChangePageActionListener){
        this.listener = listener
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        mViewModel.getTrackList().observe(this, {

            it?.let {
                currentList = it
                adapter.setOnTrendingClickedListener(object : OnPlaylistItemClickedListener {
                    override fun onTrackSelected(position: Int) {
                        currentList.pivot = position
                        mViewModel.changeList(currentList)
                        listener.changePage(0)
                    }
                })
                adapter.setData(currentList, isFirstInit)
                isFirstInit = false

            }
        })
    }


    override fun setupViews() {
        mBinding.rvCurrentList.adapter = adapter
        mBinding.rvCurrentList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



    }

}

interface ChangePageActionListener{
    fun changePage(page:Int)
}

