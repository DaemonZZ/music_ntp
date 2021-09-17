package com.mock.musictpn.ui.fragment

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHostBinding
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.adapter.MainViewPagerAdapter
import com.mock.musictpn.ui.adapter.SearchAdapter
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.viewmodel.PlayerViewModel

class HostFragment : BaseFragment<FragmentHostBinding, MainViewModel>() {

    private val titles = listOf("Home", "Trending", "Album", "Genre")
    private val icons =
        listOf(R.drawable.ic_home, R.drawable.ic_trending, R.drawable.ic_album, R.drawable.ic_genre)

    override val mViewModel: MainViewModel by activityViewModels()
    private val mPlayerViewModel by activityViewModels<PlayerViewModel>()

    override fun getLayoutRes(): Int = R.layout.fragment_host

    private lateinit var searchAdapter: SearchAdapter

    override fun setupViews() {
        mBinding.vpMain.adapter = MainViewPagerAdapter(requireActivity())
        TabLayoutMediator(mBinding.tabMain, mBinding.vpMain) { tab, index ->
            tab.text = titles[index]
            tab.setIcon(icons[index])
        }.attach()
        mBinding.vpMain.isUserInputEnabled = false
        searchAdapter = SearchAdapter {
            findNavController().navigate(R.id.action_hostFragment_to_playerFragment)
            Log.d("NganHV-HostFragment", "onClick: ${it.pivot}")
            mPlayerViewModel.apply { changeList(it) }
        }
        mBinding.rvResultSearch.adapter = searchAdapter

    }

    override fun setupListeners() {
        mBinding.imvMyFavorite.setOnClickListener {
            it.findNavController().navigate(R.id.action_hostFragment_to_favoriteFragment)
        }
        mBinding.edtSearch.setOnClickListener {
            actionSearch(true)
        }
        mBinding.imvClear.setOnClickListener {
            if (mBinding.edtSearch.text.toString().isEmpty()) {
                actionSearch(false)
            } else mBinding.edtSearch.setText("")
        }
        mBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Handler(Looper.getMainLooper()).postDelayed({
                    mViewModel.searchByKeyword(s.toString())
                }, 500)
            }

            override fun afterTextChanged(s: Editable) {
            }

        })
        mBinding.edtSearch.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                actionSearch(false)
            }
            false
        }
        mBinding.playerHolder.setOnClickListener {
            Log.d("ThangDN6 - HostFragment", "setupListeners: Open Player")
        }
    }

    override fun setupObservers() {
        mViewModel.resultSearch.observe(this) {
            it?.let {
                searchAdapter.submitList(it.search.data.tracks)
                if (mBinding.rvResultSearch.visibility == View.GONE) {
                    actionSearch(true)
                }
            }
        }
    }

    private fun actionSearch(isStart: Boolean) {
        if (!isStart) {
            mBinding.rvResultSearch.visibility = View.GONE
            mBinding.imvClear.visibility = View.GONE
            mBinding.imvMyFavorite.visibility = View.VISIBLE
            mBinding.edtSearch.setText("")
            hideKeyboardFrom(requireActivity(), mBinding.edtSearch)
        } else {
            mBinding.imvMyFavorite.visibility = View.GONE
            mBinding.imvClear.visibility = View.VISIBLE
            mBinding.rvResultSearch.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.clearData()
    }
}