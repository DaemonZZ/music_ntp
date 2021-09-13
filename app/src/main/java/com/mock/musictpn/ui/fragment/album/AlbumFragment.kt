package com.mock.musictpn.ui.fragment.album

import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentAlbumBinding
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.AlbumAdapter
import com.mock.musictpn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumFragment : BaseFragment<FragmentAlbumBinding, MainViewModel>() {

    private lateinit var albumAdapter: AlbumAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_album
    override val mViewModel: MainViewModel by activityViewModels()

    override fun setupViews() {
        albumAdapter = AlbumAdapter { }
        mBinding.rvAlbum.adapter = albumAdapter
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
        mViewModel.albumList.observe(this) {
            albumAdapter.setData(it.albums)
        }
    }

}
