package com.mock.musictpn.ui.fragment.listdetail

import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentListDetailBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.base.BaseFragment

class ListDetailFragment: BaseFragment<FragmentListDetailBinding, MainViewModel>() {
    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_list_detail

    override fun setupViews() {
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
    }
}