package com.mock.musictpn.ui.fragment

import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHostBinding
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.adapter.MainViewPagerAdapter
import com.mock.musictpn.ui.base.BaseFragment

class HostFragment : BaseFragment<FragmentHostBinding, MainViewModel>() {

    private val titles = listOf("Home", "Trending", "Album", "Genre")
    private val icons =
        listOf(R.drawable.ic_home, R.drawable.ic_trending, R.drawable.ic_album, R.drawable.ic_genre)

    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_host

    override fun setupViews() {
        mBinding.vpMain.adapter = MainViewPagerAdapter(requireActivity())
        TabLayoutMediator(mBinding.tabMain, mBinding.vpMain) { tab, index ->
            tab.text = titles[index]
            tab.setIcon(icons[index])
        }.attach()
        mBinding.vpMain.isUserInputEnabled = false
    }

    override fun setupListeners() {
        mBinding.imvSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_hostFragment_to_favoriteFragment)
        }
        mBinding.edtSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_hostFragment_to_searchFragment)
        }
    }

    override fun setupObservers() {
    }
}