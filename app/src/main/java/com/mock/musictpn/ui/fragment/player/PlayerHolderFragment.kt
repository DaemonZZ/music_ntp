package com.mock.musictpn.ui.fragment.player

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.app.service.MusicService
import com.mock.musictpn.databinding.FragmentPlayerHolderBinding
import com.mock.musictpn.mediaplayer.MusicPlayer
import com.mock.musictpn.mediaplayer.OnPlayerStateChangedListener
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.activity.MainActivity
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.viewmodel.PlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerHolderFragment : BaseFragment<FragmentPlayerHolderBinding, PlayerViewModel>() {

    @Inject
    lateinit var scope: CoroutineScope
    override val mViewModel by activityViewModels<PlayerViewModel>()
    private lateinit var mService: MusicService
    private lateinit var serviceIntent: Intent
    private lateinit var currentTracks: TrackList
    private lateinit var animation: Animator


    override fun getLayoutRes(): Int {
        return R.layout.fragment_player_holder
    }

    override fun setupViews() {

        CoroutineScope(Dispatchers.Main).launch {
            while (MainActivity.mService == null){
                delay(100)
            }
            mService = MainActivity.mService!!
            loadState()
            setUpPlayerListener()
            Log.d("ThangDN6 - PlayerHolderFragment", "setupViews: ?")
            Log.d(
                "ThangDN6 - PlayerHolderFragment",
                "loadState: hide out ${mService.musicController.isStopped()}"
            )


        }

        loadAnimation()

    }

    override fun setupListeners() {
    }

    override fun setupObservers() {
        mViewModel.getTrackList().observe(this, {
            currentTracks = it
            mBinding.track = it.tracks[it.pivot]
        })

        mViewModel.isPlaying().observe(this, {
            if (it) {
                animation.resume()
            } else {
                animation.pause()
            }
        })

    }

    private fun loadAnimation() {
        animation = ObjectAnimator.ofFloat(mBinding.imvArtist, "rotation", 0f, 360f).apply {
            duration = 10000L
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
            start()
            pause()
        }
    }


    private val playerStateChangedListener = object : OnPlayerStateChangedListener {
        override fun onStateChange() {
            CoroutineScope(Dispatchers.Main).launch { loadState() } // make provider
        }

        override fun onTrackChange() {
            mViewModel.changeList(mService.musicController.listTrack)
            //currentTracks = mService.musicController.listTrack
            CoroutineScope(Dispatchers.Main).launch {
//                loadTrackInfo()
//                upDateNotification()


            }
        }

        override fun onStartedPlaying() {
//            mBinding.seekBar.max = mService.musicController.getTrackDuration()
//            mBinding.tvTimeDuration.text = toTime(mService.musicController.getTrackDuration())
//            isPreparing = false
//            addToHistory(currentTracks.tracks[currentTracks.pivot])
        }

    }

    fun loadState() {

        if (mService.musicController.isPlaying()) {
            mBinding.imvPlay.setImageResource(R.drawable.ic_pause)
            //upDateNotification()
            mViewModel.changeState(true)
            mService.isPlaying = true
        } else {
            mBinding.imvPlay.setImageResource(R.drawable.ic_play_no_border)
            //upDateNotification()
            mViewModel.changeState(false)
            mService.isPlaying = false
        }

        Log.d(
            "ThangDN6 - PlayerHolderFragment",
            "loadState: hide out ${mService.musicController.isStopped()}"
        )
        if (mService.musicController.isStopped()) {
            mBinding.container.visibility = View.GONE
        } else {
            mBinding.container.visibility = View.VISIBLE
        }
    }

    private fun setUpPlayerListener() {
        mService.musicController.setOnPlayerStateChangedListener(playerStateChangedListener)
        //mService.musicController.setOnErrorListener(errorListener)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}