package com.mock.musictpn.ui.fragment.favorite

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentFavoriteBinding
import com.mock.musictpn.ui.adapter.FavoriteTrackAdapter
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.viewmodel.PlayerViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, MainViewModel>() {

    private lateinit var favoriteTrackAdapter: FavoriteTrackAdapter

    override val mViewModel: MainViewModel by activityViewModels()

    private val mPlayerViewModel by activityViewModels<PlayerViewModel>()

    override fun getLayoutRes(): Int = R.layout.fragment_favorite

    override fun setupViews() {
        val navController = findNavController(requireActivity(), R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        mBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
        favoriteTrackAdapter = FavoriteTrackAdapter {
            this@FavoriteFragment.findNavController()
                .navigate(R.id.action_favoriteFragment_to_hilt_PlayerFragment)
            Log.d("ThangDN6 - FavoriteFragment", "onClick: ${it.pivot}")
            mPlayerViewModel.apply {
                changeList(it)
            }
        }
        mBinding.rvMyFavorite.adapter = favoriteTrackAdapter
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        mViewModel.getFavoriteTracks().observe(this) {
            it?.let {
                favoriteTrackAdapter.submitList(it.tracks)
            }
        }
    }
}