package com.mock.musictpn.ui.fragment.favorite

import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentFavoriteBinding
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.base.BaseFragment

class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, MainViewModel>() {
    override val mViewModel: MainViewModel by activityViewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_favorite

    override fun setupViews() {
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
    }
}