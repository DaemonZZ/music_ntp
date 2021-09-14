package com.mock.musictpn.ui.fragment.home

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHomeBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.BannerAdapter
import com.mock.musictpn.ui.adapter.TrackLocalAdapter
import com.mock.musictpn.ui.base.BaseFragment
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
    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun setupViews() {
        bannerAdapter = BannerAdapter {}
        trackLocalAdapter = TrackLocalAdapter {}
        mBinding.viewPagerBanner.adapter = bannerAdapter
        mBinding.rvDeviceTracks.adapter = trackLocalAdapter
    }

    override fun setupListeners() {
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPagerBanner) { _, _ -> }.attach()
        mBinding.viewPagerBanner.registerOnPageChangeCallback(viewPagerCallback)
    }

    override fun setupObservers() {
        mViewModel.albumBanner.observe(this, { albums ->
            albums?.let {
                bannerAdapter.setData(it.albums)
                autoSwipeBanner()
                mBinding.viewPagerBanner.registerOnPageChangeCallback(viewPagerCallback)
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
    }

    private fun autoSwipeBanner() {
        val handler = Handler(Looper.getMainLooper())
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

    override fun onDestroy() {
        super.onDestroy()
        mBinding.viewPagerBanner.unregisterOnPageChangeCallback(viewPagerCallback)
    }

    private var viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentCallBack = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (currentCallBack == 4) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        mBinding.viewPagerBanner.setCurrentItem(0, false)
                    }, PERIOD_MS)
                }
            }
        }
    }
}
