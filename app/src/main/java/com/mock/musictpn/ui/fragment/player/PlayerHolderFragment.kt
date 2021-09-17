package com.mock.musictpn.ui.fragment.player

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
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
        serviceIntent = Intent(requireContext(), MusicService::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            while (MainActivity.mService == null) {
                delay(100)
                Log.d("ThangDN6 - PlayerHolderFragment", "setupViews: Wait")
            }
            mService = MainActivity.mService!!
            setUpPlayerListener()
            currentTracks = mService.musicController.listTrack

            if (currentTracks.tracks.isNotEmpty()) {
                loadState()
                if (currentTracks.tracks.isNotEmpty()){
                    loadTrackInfo()
                    mViewModel.previousState = currentTracks
                    Log.d("ThangDN6 - PlayerHolderFragment", "setupViews: Helloo ${mViewModel.previousState.tracks[mViewModel.previousState.pivot]}")
                }
            }

        }
        loadAnimation()
    }

    override fun setupListeners() {
        mBinding.imvPlay.setOnClickListener {
            val intent = serviceIntent.apply { action = MusicPlayer.ACTION_PLAY }
            requireContext().startForegroundService(intent)
        }
        mBinding.imvNext.setOnClickListener {
            val intent = serviceIntent.apply { action = MusicPlayer.ACTION_NEXT }
            requireContext().startForegroundService(intent)
        }
        mBinding.imvPrev.setOnClickListener {
            val intent = serviceIntent.apply { action = MusicPlayer.ACTION_PREV }
            requireContext().startForegroundService(intent)
        }
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
            Log.d("ThangDN6 - PlayerHolderFragment", "onStateChange: ")
        }

        override fun onTrackChange() {
            Log.d("ThangDN6 - PlayerHolderFragment", "onTrackChange: ")
            mViewModel.changeList(mService.musicController.listTrack)
            //currentTracks = mService.musicController.listTrack
            CoroutineScope(Dispatchers.Main).launch {
                loadTrackInfo()
                upDateNotification()


            }
        }

        override fun onStartedPlaying() {
            Log.d("ThangDN6 - PlayerHolderFragment", "onStartedPlaying: ")
        }

    }

    fun upDateNotification() {
        if (mService.musicController.listTrack.tracks.isNotEmpty()
        ) {

            scope.launch {
                val bitmap: Bitmap?
                val url: String = mService.musicController.getCurrentTrack().getImageUrl()!!
                if (mService.musicController.getCurrentTrack().previewURL.contains(MusicPlayer.CONTENT_LOCAL)) {
                    Log.d("ThangDN6 - PlayerHolderFragment", "upDateNotification: ")
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
                } else {
                    bitmap = loadImg(url)
                }

                mService.createNotification(
                    mService.musicController.getCurrentTrack().name,
                    bitmap!!,
                    mService.musicController.getCurrentTrack().artistName
                )
            }


        }

    }

    private fun loadImg(imagePath: String): Bitmap {
        val url = URL(imagePath)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    fun loadState() {
        if (mService.musicController.isPlaying()) {
            mBinding.imvPlay.setImageResource(R.drawable.ic_pause)
            upDateNotification()
            mViewModel.changeState(true)
            mService.isPlaying = true
        } else {
            mBinding.imvPlay.setImageResource(R.drawable.ic_play_no_border)
            upDateNotification()
            mViewModel.changeState(false)
            mService.isPlaying = false
        }

        if (mService.musicController.isStopped()) {
            mBinding.container.visibility = View.GONE
            Log.d("ThangDN6 - PlayerHolderFragment", "loadState: HIDE")
        } else {
            mBinding.container.visibility = View.VISIBLE
            Log.d("ThangDN6 - PlayerHolderFragment", "loadState: START")
        }
    }

    fun loadTrackInfo() {
        mBinding.track = currentTracks.tracks[currentTracks.pivot]
        // mBinding.seekBar.max = mService.musicController.getTrackDuration()

    }

    private fun setUpPlayerListener() {
        mService.musicController.setOnPlayerStateChangedListener(playerStateChangedListener)

        //mService.musicController.setOnErrorListener(errorListener)
    }


}