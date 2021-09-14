package com.mock.musictpn.ui.fragment.search

import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentSearchBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, MainViewModel>() {
    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_search

    override fun setupViews() {
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
    }

}