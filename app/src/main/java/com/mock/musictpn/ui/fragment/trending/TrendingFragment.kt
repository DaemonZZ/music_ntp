package com.mock.musictpn.ui.fragment.trending

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentTrendingBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.service.MusicService
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.TrendingAdapter
import com.mock.musictpn.ui.adapter.listener.OnTrendingItemClickedListener
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.ui.fragment.player.PlayerViewModel

class TrendingFragment : BaseFragment<FragmentTrendingBinding, MainViewModel>() {

    private lateinit var trendingAdapter: TrendingAdapter
    val mPlayerViewModel by activityViewModels<PlayerViewModel>()
    override val mViewModel: MainViewModel by activityViewModels()


    private lateinit var mService: MusicService

    override fun getLayoutRes(): Int = R.layout.fragment_trending
    private val connection = object : ServiceConnection {
        private var isConnected = false
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isConnected = true
            Log.d("ThangDN6 - PlayerFragment", "onServiceConnected: ")
            val binder = service as MusicService.MusicBinder
            mService = binder.getService()


        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
            Log.d("ThangDN6 - PlayerFragment", "onServiceDisconnected: ")
        }

        fun isConnected() = isConnected

    }


    override fun setupViews() {
        trendingAdapter = TrendingAdapter()
        trendingAdapter.setOnTrendingClickedListener(object : OnTrendingItemClickedListener {
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