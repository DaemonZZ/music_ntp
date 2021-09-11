package com.mock.musictpn.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentHomeBinding
import com.mock.musictpn.datasource.network.ApiContract
import com.mock.musictpn.datasource.network.IMusicService
import com.mock.musictpn.model.album.AlbumList
import com.mock.musictpn.model.image.ImageList
import com.mock.musictpn.ui.adapter.SliderAdapter
import com.mock.musictpn.ui.base.BaseFragment
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding,HomeViewModel>(){
    override val mViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var apiService:IMusicService

    private lateinit var topAlbums : AlbumList
    private lateinit var topImgs : ImageList


    private fun loadSlider(){
        mViewModel.albumList.observe(this,{
            topAlbums = it
        })
        scope.launch {

            withContext(Dispatchers.Main) {
                mBinding.sliderMain.apply {
                    setSliderAdapter(SliderAdapter(topAlbums.albums, scope, apiService))
                    setIndicatorAnimation(IndicatorAnimationType.WORM)
                    setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                    startAutoCycle()
                }
            }

        }
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun setupViews() {
        loadSlider()
    }

    override fun setupListeners() {

    }

    override fun setupObservers() {
    }

}