package com.mock.musictpn.ui.fragment.genre

import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentGenreBinding
import com.mock.musictpn.ui.adapter.GenreAdapter
import com.mock.musictpn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding, GenreViewModel>() {

    private lateinit var genreAdapter: GenreAdapter

    override val mViewModel: GenreViewModel by activityViewModels()
    override fun getLayoutRes(): Int = R.layout.fragment_genre

    override fun setupViews() {
        genreAdapter = GenreAdapter { }
        mBinding.rvGenre.adapter = genreAdapter
        mViewModel.getGenres()
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