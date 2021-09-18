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
import com.mock.musictpn.app.service.MusicService
import com.mock.musictpn.databinding.FragmentPlayerBinding
import com.mock.musictpn.mediaplayer.MusicPlayer
import com.mock.musictpn.mediaplayer.OnPlayerStateChangedListener
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.activity.MainActivity
import com.mock.musictpn.ui.adapter.DiscPagerAdapter
import com.mock.musictpn.ui.base.BaseFragment
import com.mock.musictpn.ui.fragment.player.player_inside.ChangePageActionListener
import com.mock.musictpn.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : BaseFragment<FragmentPlayerBinding, PlayerViewModel>() {

    @Inject
    lateinit var scope: CoroutineScope
    private lateinit var mService: MusicService
    private lateinit var serviceIntent: Intent
    private lateinit var currentTracks: TrackList
    private var seekTimer: Timer? = null
    private var isPreparing = false
    private lateinit var mTrack: Track
    private var mTracks: TrackList? = null
    override val mViewModel: PlayerViewModel by activityViewModels()
    override fun getLayoutRes(): Int = R.layout.fragment_player

    override fun setupViews() {
        serviceIntent = Intent(requireContext(), MusicService::class.java)

        mBinding.vpDisc.adapter = DiscPagerAdapter(requireActivity()).apply {
            setChangePageActionListener(object : ChangePageActionListener {
                override fun changePage(page: Int) {
                    mBinding.vpDisc.setCurrentItem(page, true)
                }
            })
        }

        if (MainActivity.mService != null) {
            mService = MainActivity.mService!!
            if (mViewModel.previousState.tracks.isNotEmpty()) {
                mTrack = mViewModel.previousState.tracks[mViewModel.previousState.pivot]
                loadState()
                loadTrackInfo()
                setupSeekBar()
                mViewModel.changeList(mViewModel.previousState)
            }

            setUpPlayerListener()
            Log.d("ThangDN6 - PlayerFragment", "setupViews: Seek?")
            mBinding.seekBar.max = mService.musicController.getTrackDuration()
            mBinding.tvTimeDuration.text = toTime(mService.musicController.getTrackDuration())
        }
    }

    override fun setupListeners() {
        mBinding.imvFavorite.setOnClickListener { onFavorite() }
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

                    currentTracks = trackList
                    if (currentTracks != mViewModel.previousState || mService.musicController.isStopped()){
                        sendStartAction(trackList)
                    }
                    Log.d(
                        "ThangDN6 - PlayerFragment  1",
                        "setupObservers: ${it.tracks[it.pivot].playListId}"
                    )
                    updateView(it.tracks[it.pivot])
                } else {
                    currentTracks = trackList

                    if (currentTracks == mViewModel.previousState) {
                        Log.d(
                            "ThangDN6 - PlayerFragment   2",
                            "setupObservers: ${currentTracks.tracks[currentTracks.pivot].name} "
                        )
                        updateView(currentTracks.tracks[currentTracks.pivot])
                    } else {
                        sendStartAction(trackList)
                        Log.d(
                            "ThangDN6 - PlayerFragment   3",
                            "setupObservers: ${it.tracks[it.pivot].playListId}"
                        )
                        updateView(it.tracks[it.pivot])
                    }
                }

            }

        })

        mViewModel.getFavoriteTracks().observe(this) {
            mTracks = it
            isFavorite(it)
        }
    }

    private fun isFavorite(tracks: TrackList?) {
        tracks?.let {
            var isSelect = false
            for (i in it.tracks) {
                if (i.previewURL == mTrack.previewURL) {
                    isSelect = true
                    mTrack = i
                    break
                }
            }
            mBinding.imvFavorite.isSelected = isSelect
            Log.d("ThangDN6 - PlayerFragment", "isFavorite: ${mTrack.name}  -  $isSelect")
        }
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
        mTrack = track
        Log.d("NganHV", "updateView: ${mTrack.playListId}")
        mBinding.track = track
        loadState()
        setupSeekBar()
        isFavorite(mTracks)

    }

    private fun onFavorite() {
        if (mBinding.imvFavorite.isSelected) {
            mViewModel.deleteFavoriteTrack(mTrack)
            return
        }
        mViewModel.insertFavoriteTrack(mTrack)
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
            mService.isPlaying = true
        } else {
            mBinding.btnPlay.setImageResource(R.drawable.ic_play1)
            upDateNotification()
            mViewModel.changeState(false)
            mService.isPlaying = false
        }

        if (mService.musicController.isStopped()) {
            mBinding.seekBar.progress = 0
            if (activity == null) {
                mService.stopSelf()
            }
        }
    }

    fun loadTrackInfo() {
        mTrack = mService.musicController.getCurrentTrack()
        mBinding.track = mTrack
        isFavorite(mTracks)

        Log.d("ADD", "loadTrackInfo: $mTrack")

    }

    private fun setupSeekBar() {
        seekTimer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (!mService.musicController.isStopped()) {
                        mBinding.seekBar.progress = mService.musicController.getCurrentPosition()
                        CoroutineScope(Dispatchers.Main).launch {
                            mBinding.tvTimeProgress.text =
                                toTime(mService.musicController.getCurrentPosition())
                        }

                    }

                }
            }, 0, 100)
        }
    }

    fun upDateNotification() {
        if (mService.musicController.listTrack.tracks.isNotEmpty()
        ) {

            scope.launch {
                val bitmap: Bitmap?
                val url: String = mService.musicController.getCurrentTrack().getImageUrl()!!
                if (mService.musicController.getCurrentTrack().previewURL.contains(MusicPlayer.CONTENT_LOCAL)) {
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
            addToHistory(currentTracks.tracks[currentTracks.pivot])
        }

    }

    private fun addToHistory(track: Track) {
        scope.launch {
            val historyList = mViewModel.getHistoryTracks()
            var lastID = 0
            var isExist = false
            Log.d("ThangDN6 - PlayerFragment", "addToHistory: ${historyList.tracks.size}")
            for (i in historyList.tracks.indices) {
                if (historyList.tracks[i].previewURL == track.previewURL) {
                    isExist = true
                }
            }
            if (historyList.tracks.size > 20) lastID =
                historyList.tracks[historyList.tracks.size - 20].localId
            if (isExist) {
                Log.d("ThangDN6 - PlayerFragment", "addToHistory: IsExist")
                mViewModel.insertHistoryTrack(track, isExist)
            } else {
                Log.d("ThangDN6 - PlayerFragment", "addToHistory: NotExist")
                mViewModel.insertHistoryTrack(track, lastID)
            }
        }
    }


    override fun onDestroy() {
        Log.d("ThangDN6 - PlayerFragment", "onDestroy: ")
        mViewModel.previousState = currentTracks
        seekTimer?.cancel()
        super.onDestroy()
    }


}