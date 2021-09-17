package com.mock.musictpn.ui.fragment.listdetail

import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentListDetailBinding
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.genre.Genre
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.viewmodel.MainViewModel
import com.mock.musictpn.ui.adapter.TrackByTypeAdapter
import com.mock.musictpn.ui.adapter.listener.OnPlaylistItemClickedListener
import com.mock.musictpn.ui.adapter.listener.OnTrackItemClickedListener
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.utils.Const.EXTRA_ALBUM
import com.mock.musictpn.utils.Const.EXTRA_GENRE
import com.mock.musictpn.viewmodel.PlayerViewModel

class ListDetailFragment : BaseFragment<FragmentListDetailBinding, MainViewModel>() {

    private lateinit var trackByTypeAdapter: TrackByTypeAdapter
    private val mPlayerViewModel by activityViewModels<PlayerViewModel>()
    private lateinit var mTrackList: TrackList
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
        trackByTypeAdapter = TrackByTypeAdapter().apply {
            setOnTrackItemClickedListener(object : OnTrackItemClickedListener{
                override fun onClick(tracks: TrackList) {
                    this@ListDetailFragment.findNavController()
                        .navigate(R.id.action_listDetailFragment_to_hilt_PlayerFragment)
                    Log.d("ThangDN6 - ListDetailFragment", "onClick: ${tracks.pivot}")
                    mPlayerViewModel.apply {
                        changeList(tracks)

                    }
                }

            })
        }
        mBinding.rvTracksByType.adapter = trackByTypeAdapter
    }



    override fun setupListeners() {
        mBinding.btnPlayShuffle.setOnClickListener {
            this@ListDetailFragment.findNavController()
                .navigate(R.id.action_listDetailFragment_to_hilt_PlayerFragment)
            mPlayerViewModel.apply {
                changeList(mTrackList)}
        }
    }




    override fun setupObservers() {
        mViewModel.tracksByAlbumId.observe(this) {
            it?.let {
                trackByTypeAdapter.submitList(it.tracks)
                mTrackList = TrackList(it.tracks,it.tracks.indices.random())
            }
        }
        mViewModel.tracksByGenreId.observe(this) {
            it?.let {
                trackByTypeAdapter.submitList(it.tracks)
                Log.d("UUU", "setupObservers: $it")
                mTrackList = TrackList(it.tracks,it.tracks.indices.random())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.clearData()
    }

}
