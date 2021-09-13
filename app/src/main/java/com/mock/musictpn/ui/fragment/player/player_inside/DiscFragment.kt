package com.mock.musictpn.ui.fragment.player.player_inside

import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentDiscBinding
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.ui.fragment.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscFragment : BaseFragment<FragmentDiscBinding,PlayerViewModel>() {
    override val mViewModel: PlayerViewModel by activityViewModels()
    private lateinit var animation:Animator



    override fun getLayoutRes(): Int {
        return R.layout.fragment_disc
    }

    override fun setupListeners() {

    }

    override fun setupObservers() {
        mViewModel.isPlaying().observe(this,{
            if(it){
                animation.resume()
            } else {
                animation.pause()
            }
        })

        mViewModel.getCurrentTrack().observe(this,{
            mBinding.track = it
        })
    }

    override fun setupViews() {
        loadAnimation()
    }

    private fun loadAnimation(){
        animation = ObjectAnimator.ofFloat(mBinding.imgCover, "rotation", 0f, 360f).apply {
            duration = 15000L
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            start()
            pause()
        }
    }

}