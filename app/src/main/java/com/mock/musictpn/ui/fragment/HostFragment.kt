package com.mock.musictpn.ui.fragment

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHostBinding
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.adapter.MainViewPagerAdapter
import com.mock.musictpn.ui.adapter.TrackByTypeAdapter
import com.mock.musictpn.ui.base.BaseFragment

class HostFragment : BaseFragment<FragmentHostBinding, MainViewModel>() {

    private val titles = listOf("Home", "Trending", "Album", "Genre")
    private val icons =
        listOf(R.drawable.ic_home, R.drawable.ic_trending, R.drawable.ic_album, R.drawable.ic_genre)

    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_host

    private lateinit var trackByTypeAdapter: TrackByTypeAdapter

    override fun setupViews() {
        mBinding.vpMain.adapter = MainViewPagerAdapter(requireActivity())
        TabLayoutMediator(mBinding.tabMain, mBinding.vpMain) { tab, index ->
            tab.text = titles[index]
            tab.setIcon(icons[index])
        }.attach()
        mBinding.vpMain.isUserInputEnabled = false
        trackByTypeAdapter = TrackByTypeAdapter()
        mBinding.rvResultSearch.adapter = trackByTypeAdapter
    }

    override fun setupListeners() {
        mBinding.imvSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_hostFragment_to_favoriteFragment)
        }
        mBinding.edtSearch.setOnClickListener {
            if (mBinding.rvResultSearch.visibility == View.GONE){
                mBinding.rvResultSearch.visibility = View.VISIBLE
            }
        }

        mBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Handler(Looper.getMainLooper()).postDelayed({
                    mViewModel.searchByKeyword(s.toString())
                },500)
            }

            override fun afterTextChanged(s: Editable) {
            }

        })
    }

    override fun setupObservers() {
        mViewModel.resultSearch.observe(this){
            it?.let {

            }
        }
    }
}