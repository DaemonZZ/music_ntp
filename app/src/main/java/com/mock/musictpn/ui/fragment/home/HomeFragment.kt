package com.mock.musictpn.ui.fragment.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHomeBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.adapter.BannerAdapter
import com.mock.musictpn.ui.adapter.HistoryAdapter
import com.mock.musictpn.ui.adapter.TrackLocalAdapter
import com.mock.musictpn.ui.adapter.listener.OnTrackItemClickedListener
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.utils.Const
import com.mock.musictpn.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    var currentPage = 0
    var currentCallBack = 0
    private val timer: Timer by lazy { Timer() }

    companion object {
        const val DELAY_MS: Long = 500
        const val PERIOD_MS: Long = 3000
    }

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var trackLocalAdapter: TrackLocalAdapter
    private lateinit var historyAdapter: HistoryAdapter
    override val mViewModel: MainViewModel by activityViewModels()
    private val mPlayerViewModel by activityViewModels<PlayerViewModel>()
    private lateinit var handler: Handler
    private lateinit var mTracksHistory: List<Track>
    private var isShowMore = false

    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun setupViews() {
        handler = Handler(Looper.getMainLooper())
        bannerAdapter = BannerAdapter {
            val bundle = bundleOf(Const.EXTRA_ALBUM to it)
            findNavController().navigate(R.id.action_hostFragment_to_listDetailFragment, bundle)
        }
        trackLocalAdapter = TrackLocalAdapter().apply {
            setOnTrackItemClickedListener(object : OnTrackItemClickedListener {
                override fun onClick(tracks: TrackList) {
                    this@HomeFragment.findNavController()
                        .navigate(R.id.action_hostFragment_to_playerFragment)
                    Log.d("ThangDN6 - TrendingFragment", "onClick: ${tracks.pivot}")
                    mPlayerViewModel.apply {
                        changeList(tracks)
                    }
                }
            })
        }
        historyAdapter = HistoryAdapter {
            findNavController().navigate(R.id.action_hostFragment_to_playerFragment)
            mPlayerViewModel.apply {
                TrackList(mTracksHistory, it.pivot).apply {
                    changeList(this)
                }
            }
        }
        mBinding.viewPagerBanner.adapter = bannerAdapter
        mBinding.rvDeviceTracks.adapter = trackLocalAdapter
        mBinding.rvHistory.adapter = historyAdapter
        mBinding.viewPagerBanner.registerOnPageChangeCallback(viewPagerCallback)

    }

    override fun setupListeners() {
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPagerBanner) { _, _ -> }.attach()
        mBinding.imvExpandHistory.setOnClickListener {
            if (!isShowMore) {
                mBinding.imvExpandHistory.isSelected = true
                isShowMore = true
                setDataHistory(mTracksHistory)
            } else {
                mBinding.imvExpandHistory.isSelected = true
                isShowMore = false
                setDataHistory(mTracksHistory)
            }

        }
    }

    override fun setupObservers() {
        mViewModel.albumBanner.observe(this, { albums ->
            albums?.let {
                bannerAdapter.setData(it.albums)
                autoSwipeBanner()
            }
        })

        mViewModel.isLoading.observe(this) { isShow: Boolean ->
            showLoading(isShow)
        }
        mViewModel.errorMessage.observe(this) { message: String? ->
            message?.let {
                showError(it)
            }
        }
        mViewModel.tracksLocal.observe(this) { tracks ->
            tracks?.let {
                trackLocalAdapter.setData(it)
            }
        }
        mViewModel.getAllHistory(20).observe(this) {
            it?.let {
                mTracksHistory = it
                setDataHistory(it)
            }
        }
    }

    private fun setDataHistory(tracks: List<Track>) {
        if (tracks.size <= 5) {
            historyAdapter.submitList(tracks)
            mBinding.imvExpandHistory.visibility = View.GONE
            mBinding.titleAction.visibility = View.GONE
        } else {
            if (isShowMore) {
                historyAdapter.submitList(tracks)
                mBinding.titleAction.text = getString(R.string.show_less)
            } else {
                historyAdapter.submitList(tracks.take(5))
                mBinding.titleAction.text = getString(R.string.show_more)
            }
            mBinding.imvExpandHistory.visibility = View.VISIBLE
            mBinding.titleAction.visibility = View.VISIBLE
        }
    }

    private fun autoSwipeBanner() {
        val update = Runnable {
            if (currentCallBack == 0) {
                currentPage = 1
            }
            mBinding.viewPagerBanner.setCurrentItem(currentPage++, true)
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }

        }, DELAY_MS, PERIOD_MS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.viewPagerBanner.unregisterOnPageChangeCallback(viewPagerCallback)
    }

    private var viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentCallBack = position
            currentPage = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (currentCallBack == 4 && currentPage == 4) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (currentCallBack == 4) {
                            mBinding.viewPagerBanner.setCurrentItem(0, false)
                        }
                    }, PERIOD_MS)
                }
            }
        }
    }
}
