package com.mock.musictpn.ui.fragment.listdetail

import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentListDetailBinding
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.genre.Genre
import com.mock.musictpn.ui.activity.MainViewModel
import com.mock.musictpn.ui.adapter.TrackByTypeAdapter
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.utils.Const.EXTRA_ALBUM
import com.mock.musictpn.utils.Const.EXTRA_GENRE

class ListDetailFragment : BaseFragment<FragmentListDetailBinding, MainViewModel>() {

    private lateinit var trackByTypeAdapter: TrackByTypeAdapter
    override val mViewModel: MainViewModel by activityViewModels()
    override fun getLayoutRes(): Int = R.layout.fragment_list_detail

    override fun setupViews() {
        arguments?.let {
            if (it.containsKey(EXTRA_GENRE)) {
                val genre = it.getSerializable(EXTRA_GENRE) as Genre
                mViewModel.getTracksByGenreId(genre.id)
                mBinding.linkImage = genre.getImageUrl()
                mBinding.collapsingLayout.title = genre.name
            } else if (it.containsKey(EXTRA_ALBUM)) {
                val album = it.getSerializable(EXTRA_ALBUM) as Album
                mViewModel.getTracksByAlbumId(album.id)
                mBinding.linkImage = album.getImageUrl()
                mBinding.collapsingLayout.title = album.name
            }
        }
        val heightDp = (resources.displayMetrics.heightPixels / 2.5).toFloat()
        val lp = mBinding.appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = heightDp.toInt()
        mBinding.collapsingLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        mBinding.collapsingLayout.setExpandedTitleColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        trackByTypeAdapter = TrackByTypeAdapter {}
        mBinding.rvTracksByType.adapter = trackByTypeAdapter
    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        mViewModel.tracksByAlbumId.observe(this) {
            it?.let {
                trackByTypeAdapter.submitList(it.tracks)
            }
        }
        mViewModel.tracksByGenreId.observe(this) {
            it?.let {
                trackByTypeAdapter.submitList(it.tracks)
                Log.d("UUU", "setupObservers: $it")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.clearData()
    }

}
