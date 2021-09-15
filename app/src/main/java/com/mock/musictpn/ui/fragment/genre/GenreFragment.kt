package com.mock.musictpn.ui.fragment.genre

import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentGenreBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.GenreAdapter
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.utils.Const
import com.mock.musictpn.utils.Const.EXTRA_GENRE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding, MainViewModel>() {

    private lateinit var genreAdapter: GenreAdapter

    override val mViewModel: MainViewModel by activityViewModels()
    override fun getLayoutRes(): Int = R.layout.fragment_genre

    override fun setupViews() {
        genreAdapter = GenreAdapter {
            val bundle = bundleOf(EXTRA_GENRE to it)
            findNavController().navigate(R.id.action_hostFragment_to_listDetailFragment, bundle)
        }
        mBinding.rvGenre.adapter = genreAdapter
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
        mViewModel.genreList.observe(this) {
            genreAdapter.setData(it.genres)
        }
    }
}