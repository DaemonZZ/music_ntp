package com.mock.musictpn.ui.fragment.home

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHomeBinding
import com.mock.musictpn.ui.adapter.BannerAdapter
import com.mock.musictpn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var bannerAdapter: BannerAdapter
    override val mViewModel: HomeViewModel by viewModels()
    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun setupViews() {
        mViewModel.getAlbums()
        bannerAdapter = BannerAdapter{}
        mBinding.viewPager.adapter = bannerAdapter
    }

    override fun setupListeners() {
        TabLayoutMediator(mBinding.tabLayout,mBinding.viewPager){ _, _ -> }.attach()
    }

    override fun setupObservers() {
        mViewModel.albumList.observe(this, {
            bannerAdapter.setData(it.albums)
        })
    }

}