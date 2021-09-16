package com.mock.musictpn.ui.adapter

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ItemTrackBinding
import com.mock.musictpn.databinding.ItemTrackLocalBinding
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.adapter.listener.OnTrackItemClickedListener
import kotlinx.coroutines.*
import javax.inject.Inject

class TrackLocalAdapter : RecyclerView.Adapter<TrackLocalAdapter.ViewHolder>() {
    private lateinit var listener: OnTrackItemClickedListener
    private var tracks = listOf<Track>()

    fun setData(newList: List<Track>){
        tracks = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackLocalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])
        val trackList = TrackList(tracks,position)
        holder.binding.root.setOnClickListener { listener.onClick(trackList) }
    }

    override fun getItemCount(): Int = tracks.size

    fun setOnTrackItemClickedListener(listener: OnTrackItemClickedListener){
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemTrackLocalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.track = track
//            coroutineScope.launch {
//                try {
//                    val x = CoroutineScope(Dispatchers.IO).async {
//                        track.albumId?.let {
//                            getAlbumArtwork(binding.imvArtist.context.contentResolver, it.toLong())
//                        }
//                    }
//                    withContext(Dispatchers.Main){
//                        binding.imvArtist.setImageBitmap(x.await())
//                    }
//                }catch (ex: Exception){
//                    //Không nên xử lý trycatch như này
//                    binding.imvArtist.setImageResource(R.drawable.artist_sample)
//                }
//
//            }
        }
    }

    private fun getAlbumArtwork(resolver: ContentResolver, albumId: Long): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentUri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)
            resolver.loadThumbnail(contentUri, Size(640, 480), null)
        } else {
            null
        // làm 1 cái gì đó ở đây
        // Glide.with(this)
        //.load("content://media/external/audio/albums/5469649338118982769".toUri())// Uri of the picture
        //.into(mBinding.rvRecentTracks)
        }
    }



}