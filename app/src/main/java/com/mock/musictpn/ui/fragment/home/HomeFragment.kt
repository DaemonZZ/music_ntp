package com.mock.musictpn.ui.fragment.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHomeBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.BannerAdapter
import com.mock.musictpn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {

    private lateinit var bannerAdapter: BannerAdapter
    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun setupViews() {
        bannerAdapter = BannerAdapter {}
        mBinding.viewPager.adapter = bannerAdapter
    }

    override fun setupListeners() {
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { _, _ -> }.attach()
    }

    override fun setupObservers() {
        mViewModel.albumList.observe(this, {
            it?.let {
                bannerAdapter.setData(it.albums)
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
            Log.d("ADR", "HOMEFRAGMENT: VO")

            for (i in tracks) {
                Log.d("ADR", "HOMEFRAGMENT: ${i.name}")
            }
        }
    }

}