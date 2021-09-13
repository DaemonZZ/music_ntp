package com.mock.musictpn.ui.fragment.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentPlayerBinding
import com.mock.musictpn.mediaplayer.MusicPlayer
import com.mock.musictpn.mediaplayer.OnPlayerStateChangedListener
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.service.MusicService
import com.mock.musictpn.ui.adapter.DiscPagerAdapter
import com.mock.musictpn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : BaseFragment<FragmentPlayerBinding, PlayerViewModel>() {

    override val mViewModel: PlayerViewModel by activityViewModels()

    private lateinit var mService: MusicService

    @Inject
    lateinit var scope: CoroutineScope

    private lateinit var serviceIntent: Intent

    private val connection = object : ServiceConnection {
        private var isConnected = false
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isConnected = true
            Log.d("ThangDN6 - PlayerFragment", "onServiceConnected: ")
            val binder = service as MusicService.MusicBinder
            mService = binder.getService()

            mService.musicController.setOnPlayerStateChangedListener(object :
                OnPlayerStateChangedListener {
                override fun onStateChange() {
                    CoroutineScope(Dispatchers.Main).launch { loadState() } // make provider
                }

                override fun onTrackChange() {
                    CoroutineScope(Dispatchers.Main).launch {
                        loadTrackInfo()
                        upDateNotification()
                    }

                }

                override fun onStartedPlaying() {
                    mBinding.seekBar.max = mService.musicController.getTrackDuration()
                }

            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
            Log.d("ThangDN6 - PlayerFragment", "onServiceDisconnected: ")
        }

        fun isConnected() = isConnected

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ThangDN6 - PlayerFragment", "onViewCreated: ")
        serviceIntent = Intent(requireContext(), MusicService::class.java)
        scope.launch {
            mViewModel.loadAlbum("alb.611303574")
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_player
    }

    override fun setupViews() {
        val intent = Intent(requireContext(), MusicService::class.java)
        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        mBinding.vpDisc.adapter = DiscPagerAdapter(requireActivity())

    }

    override fun setupListeners() {
        mBinding.btnShuffle.setOnClickListener { toggleShuffle() }
        mBinding.btnPrev.setOnClickListener { onPrev() }
        mBinding.btnPlay.setOnClickListener { togglePlay() }
        mBinding.btnNext.setOnClickListener { onNext() }
        mBinding.btnRepeat.setOnClickListener { toggleRepeat() }
        mBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(seekBar!=null){
                    mService.musicController.seekTo(seekBar.progress)
                }
            }

        })
    }

    override fun setupObservers() {
        mViewModel.getTrackList().observe(this, {
            val bundle = Bundle().apply {
                putSerializable("list", it)
                putInt("selectedIndex",0)
            }

            val intent = serviceIntent.apply {
                action = MusicPlayer.ACTION_START
                putExtras(bundle)
            }
            requireContext().startForegroundService(intent)
            updateView(it.tracks[0])


        })
    }

    private fun updateView(track: Track) {
        mBinding.track = track
        loadState()
        setupSeekBar()
    }

    private fun togglePlay() {
        val intent = serviceIntent.apply { action = MusicPlayer.ACTION_PLAY }
        requireContext().startForegroundService(intent)
    }

    private fun onNext() {
        val intent = serviceIntent.apply { action = MusicPlayer.ACTION_NEXT }
        requireContext().startForegroundService(intent)
    }

    private fun onPrev() {
        val intent = serviceIntent.apply { action = MusicPlayer.ACTION_PREV }
        requireContext().startForegroundService(intent)
    }

    private fun toggleShuffle() {
        val intent = serviceIntent.apply { action = MusicPlayer.ACTION_SHUFFLE }
        requireContext().startForegroundService(intent)
    }

    private fun toggleRepeat() {
        val intent = serviceIntent.apply { action = MusicPlayer.ACTION_REPEAT }
        requireContext().startForegroundService(intent)
    }

    fun loadState() {
        if (mService.musicController.isShuffle())
            mBinding.btnShuffle.setImageResource(R.drawable.shuffle)
        else mBinding.btnShuffle.setImageResource(R.drawable.ic_not_shuffle)

        when (mService.musicController.getRepeatMode()) {
            MusicPlayer.MODE_NO_REPEAT -> mBinding.btnRepeat.setImageResource(R.drawable.ic_not_shuffle)
            MusicPlayer.MODE_REPEAT_ONE_TRACK -> mBinding.btnRepeat.setImageResource(R.drawable.ic_repeat_1)
            MusicPlayer.MODE_REPEAT_WHOLE_LIST -> mBinding.btnRepeat.setImageResource(R.drawable.ic_repeat)
        }

        if (mService.musicController.isPlaying()) {
            mBinding.btnPlay.setImageResource(R.drawable.pause)
            upDateNotification()
            mViewModel.changeState(true)
        } else {
            mBinding.btnPlay.setImageResource(R.drawable.play1)
            upDateNotification()
            mViewModel.changeState(false)
        }

    }

    fun loadTrackInfo() {
        mBinding.track = mService.musicController.getCurrentTrack()
        mViewModel.loadCurrent(mService.musicController.getCurrentTrack())
        // mBinding.seekBar.max = mService.musicController.getTrackDuration()
    }

    private fun setupSeekBar() {
        Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    mBinding.seekBar.progress = mService.musicController.getCurrentPosition()
                }
            }, 0, 100)
        }
    }

    fun upDateNotification() {
        if (mService.musicController.listTrack.tracks.isNotEmpty())
            Glide.with(this)
                .asBitmap()
                .load(mService.musicController.getCurrentTrack().getImageUrl())
                .placeholder(R.drawable.sky)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        mService.createNotification(
                            mService.musicController.getCurrentTrack().name,
                            resource,
                            "Some one"
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        Log.d("ThangDN6 - MusicService", "onLoadCleared: cleared")
                    }

                })

    }

}