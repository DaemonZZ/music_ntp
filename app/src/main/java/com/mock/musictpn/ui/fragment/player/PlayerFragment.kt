package com.mock.musictpn.ui.fragment.player

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.mock.musictpn.R
import com.mock.musictpn.databinding.FragmentPlayerBinding
import com.mock.musictpn.mediaplayer.MusicPlayer
import com.mock.musictpn.mediaplayer.OnPlayerStateChangedListener
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.service.MusicService
import com.mock.musictpn.ui.activity.MainActivity
import com.mock.musictpn.ui.adapter.DiscPagerAdapter
import com.mock.musictpn.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : BaseFragment<FragmentPlayerBinding, PlayerViewModel>() {


    override val mViewModel: PlayerViewModel by activityViewModels()

    private lateinit var mService: MusicService

    @Inject
    lateinit var scope: CoroutineScope

    private lateinit var serviceIntent: Intent

    private lateinit var currentTracks: TrackList

    private var isPreparing = false

    override fun getLayoutRes(): Int {
        return R.layout.fragment_player
    }

    override fun setupViews() {
        serviceIntent = Intent(requireContext(), MusicService::class.java)

        mBinding.vpDisc.adapter = DiscPagerAdapter(requireActivity())
        if (MainActivity.mService != null) {
            mService = MainActivity.mService!!
            setUpPlayerListener()
        }

    }

    override fun setupListeners() {
        mBinding.btnShuffle.setOnClickListener { toggleShuffle() }
        mBinding.btnPrev.setOnClickListener { onPrev() }
        mBinding.btnPlay.setOnClickListener { togglePlay() }
        mBinding.btnNext.setOnClickListener { onNext() }
        mBinding.btnRepeat.setOnClickListener { toggleRepeat() }
        mBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    mBinding.tvTimeProgress.text = toTime(seekBar.progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    mBinding.tvTimeProgress.text = toTime(seekBar.progress)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    mService.musicController.seekTo(seekBar.progress)
                }
            }

        })
    }

    override fun setupObservers() {
        mViewModel.getTrackList().observe(this, {
            it?.let { trackList ->
                if (::currentTracks.isInitialized) {
                    Log.d(
                        "ThangDN6 - PlayerFragment",
                        "setupObservers: changed list ${currentTracks.pivot}"
                    )
                    currentTracks = trackList

                    sendStartAction(trackList)
                    updateView(it.tracks[it.pivot])
                } else {
                    currentTracks = trackList
                    Log.d(
                        "ThangDN6 - PlayerFragment",
                        "setupObservers: changed list ${currentTracks.pivot}"
                    )
                    if (currentTracks == mViewModel.previousState) {
                        updateView(currentTracks.tracks[currentTracks.pivot])
                    } else {
                        sendStartAction(trackList)
                        updateView(it.tracks[it.pivot])
                    }
                }

            }

        })


    }

    private fun sendStartAction(tracks: TrackList) {
        val bundle = Bundle().apply {
            putSerializable("list", tracks)
        }

        val intent = serviceIntent.apply {
            action = MusicPlayer.ACTION_START
            putExtras(bundle)
        }
        requireContext().startForegroundService(intent)
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
            mBinding.btnShuffle.setImageResource(R.drawable.ic_shuffle)
        else mBinding.btnShuffle.setImageResource(R.drawable.ic_not_shuffle)

        when (mService.musicController.getRepeatMode()) {
            MusicPlayer.MODE_NO_REPEAT -> mBinding.btnRepeat.setImageResource(R.drawable.ic_not_shuffle)
            MusicPlayer.MODE_REPEAT_ONE_TRACK -> mBinding.btnRepeat.setImageResource(R.drawable.ic_repeat_1)
            MusicPlayer.MODE_REPEAT_WHOLE_LIST -> mBinding.btnRepeat.setImageResource(R.drawable.ic_repeat)
        }

        if (mService.musicController.isPlaying()) {
            mBinding.btnPlay.setImageResource(R.drawable.ic_pause)
            upDateNotification()
            mViewModel.changeState(true)
        } else {
            mBinding.btnPlay.setImageResource(R.drawable.ic_play1)
            upDateNotification()
            mViewModel.changeState(false)
        }

    }

    fun loadTrackInfo() {
        mBinding.track = mService.musicController.getCurrentTrack()
        // mBinding.seekBar.max = mService.musicController.getTrackDuration()
    }

    private fun setupSeekBar() {
        Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    mBinding.seekBar.progress = mService.musicController.getCurrentPosition()
                    CoroutineScope(Dispatchers.Main).launch {
                        mBinding.tvTimeProgress.text =
                            toTime(mService.musicController.getCurrentPosition())
                    }
                }
            }, 0, 100)
        }
    }

    fun upDateNotification() {
        if (mService.musicController.listTrack.tracks.isNotEmpty() && mService.musicController.getCurrentTrack()
                .getImageUrl() != null
        ) {
            val url: String = mService.musicController.getCurrentTrack().getImageUrl()!!
            scope.launch {
                mService.createNotification(
                    mService.musicController.getCurrentTrack().name,
                    loadImg(url),
                    mService.musicController.getCurrentTrack().artistName
                )
            }


        }

    }

    private fun loadImg(imagePath: String): Bitmap {
        val url = URL(imagePath)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    fun toTime(duration: Int): String {
        val secondsCount = duration / 1000
        val min = if (secondsCount / 60 > 9) "${secondsCount / 60}" else "0${secondsCount / 60}"
        val sec = if (secondsCount % 60 > 9) "${secondsCount % 60}" else "0${secondsCount % 60}"

        return "$min:$sec"
    }

    private fun setUpPlayerListener() {
        mService.musicController.setOnPlayerStateChangedListener(playerStateChangedListener)
        mService.musicController.setOnErrorListener(errorListener)
    }

    private val errorListener = MediaPlayer.OnErrorListener { _, what, extra ->
        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
            showError("MEDIA_ERROR_SERVER_DIED - Error code: $extra")

        } else {
            Log.e("ThangDN6", ": Unknown error - code: $extra")
        }
        true
    }

    private val playerStateChangedListener = object : OnPlayerStateChangedListener {
        override fun onStateChange() {
            CoroutineScope(Dispatchers.Main).launch { loadState() } // make provider
        }

        override fun onTrackChange() {
            mViewModel.changeList(mService.musicController.listTrack)
            //currentTracks = mService.musicController.listTrack
            CoroutineScope(Dispatchers.Main).launch {
                loadTrackInfo()
                upDateNotification()

            }
        }

        override fun onStartedPlaying() {
            mBinding.seekBar.max = mService.musicController.getTrackDuration()
            mBinding.tvTimeDuration.text = toTime(mService.musicController.getTrackDuration())
            isPreparing = false
        }

    }

    override fun onDestroy() {
        Log.d("ThangDN6 - PlayerFragment", "onDestroy: ")
        mViewModel.previousState = currentTracks
        super.onDestroy()
    }


}