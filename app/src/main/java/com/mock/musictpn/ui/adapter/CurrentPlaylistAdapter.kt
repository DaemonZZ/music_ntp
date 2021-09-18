package com.mock.musictpn.ui.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ItemCurrentListBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.adapter.listener.OnPlaylistItemClickedListener

class CurrentPlaylistAdapter() :
    RecyclerView.Adapter<PlaylistViewHolder>() {
    private lateinit var listener: OnPlaylistItemClickedListener
    private lateinit var tracks: TrackList
    private var lastPivot = -1


    fun setData(tracks : TrackList , isNecessaryToReload:Boolean){
        Log.d("ThangDN6 - CurrentPlaylistAdapter", "setData: ")
        this.tracks = tracks
        if(isNecessaryToReload){
            notifyDataSetChanged()
        }else{
            notifyItemChanged(tracks.pivot)
            notifyItemChanged(lastPivot)
        }
        lastPivot = tracks.pivot

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            ItemCurrentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder,  position: Int) {
        holder.bind(tracks.tracks[position])
        holder.binding.root.setOnClickListener {
            listener.onTrackSelected(position)
        }
        holder.highLightViewHolder(position == tracks.pivot)
    }


    override fun getItemCount(): Int {
        return tracks.tracks.size
    }

    fun setOnTrendingClickedListener(listener: OnPlaylistItemClickedListener) {
        this.listener = listener
    }
}

class PlaylistViewHolder(val binding: ItemCurrentListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(track: Track) {
        binding.track = track
    }

    fun highLightViewHolder(isSelected:Boolean){
        if( isSelected){
            binding.tvName.apply {
                setTypeface(null,Typeface.BOLD_ITALIC)
                setTextColor(Color.MAGENTA)
            }
            binding.tvArtist.apply {
                setTypeface(null,Typeface.BOLD_ITALIC)
                setTextColor(Color.MAGENTA)
            }
            binding.imvPlay.setBackgroundResource(R.drawable.ic_pause)
        }
        else {
            binding.tvName.apply {
                setTypeface(null,Typeface.BOLD)
                setTextColor(Color.BLACK)
            }
            binding.tvArtist.apply {
                setTypeface(null,Typeface.BOLD)
                setTextColor(Color.BLACK)
            }
            binding.imvPlay.setBackgroundResource(R.drawable.ic_play1)
        }
    }


}
