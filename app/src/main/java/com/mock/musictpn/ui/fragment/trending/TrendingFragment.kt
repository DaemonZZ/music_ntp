package com.mock.musictpn.ui.fragment.trending

import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentTrendingBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.TrendingAdapter
import com.mock.musictpn.ui.base.BaseFragment

class TrendingFragment : BaseFragment<FragmentTrendingBinding, MainViewModel>() {

    private lateinit var trendingAdapter: TrendingAdapter

    override val mViewModel: MainViewModel by activityViewModels()
    override fun getLayoutRes(): Int = R.layout.fragment_trending

    override fun setupViews() {
        trendingAdapter = TrendingAdapter {}
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