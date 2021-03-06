package com.mock.musictpn.ui.fragment.player.player_inside

import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentDiscBinding
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscFragment : BaseFragment<FragmentDiscBinding, PlayerViewModel>() {
    override val mViewModel: PlayerViewModel by activityViewModels()
    private lateinit var animation:Animator

    private lateinit var listener: ChangePageActionListener

    fun setChangePageActionListener(listener: ChangePageActionListener){
        this.listener = listener
    }

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

        mViewModel.getTrackList().observe(this,{
            mBinding.track = it.tracks[it.pivot]
        })
    }

    override fun setupViews() {
        loadAnimation()
        mBinding.btnList.setOnClickListener {
            listener.changePage(1)
        }
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