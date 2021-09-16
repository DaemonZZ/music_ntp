package com.mock.musictpn.ui.fragment.trending

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentTrendingBinding
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.adapter.TrendingAdapter
import com.mock.musictpn.ui.adapter.listener.OnTrackItemClickedListener
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.viewmodel.PlayerViewModel

class TrendingFragment : BaseFragment<FragmentTrendingBinding, MainViewModel>() {

    private lateinit var trendingAdapter: TrendingAdapter
    private val mPlayerViewModel by activityViewModels<PlayerViewModel>()
    override val mViewModel: MainViewModel by activityViewModels()


    override fun getLayoutRes(): Int = R.layout.fragment_trending

    override fun setupViews() {
        trendingAdapter = TrendingAdapter()
        trendingAdapter.setOnTrackItemClickedListener(object : OnTrackItemClickedListener {
            override fun onClick(tracks: TrackList) {

                this@TrendingFragment.findNavController()
                    .navigate(R.id.action_hostFragment_to_playerFragment)
                Log.d("ThangDN6 - TrendingFragment", "onClick: ${tracks.pivot}")
                mPlayerViewModel.apply {
                    changeList(tracks)

                }
            }

        })
        mBinding.rvTrending.adapter = trendingAdapter
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        mViewModel.isLoading.observe(this) { isShow: Boolean ->
            showLoading(isShow)
        }
        mViewModel.errorMessage.observe(this) { message: String? ->
            message?.let {
                showError(it)
            }
        }

        mViewModel.trackList.observe(this) {
            trendingAdapter.setData(it.tracks)
            mBinding.trackList = it
        }
    }
}