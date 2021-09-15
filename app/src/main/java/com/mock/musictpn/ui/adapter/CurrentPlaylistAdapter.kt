package com.mock.musictpn.ui.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemCurrentListBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.ui.adapter.listener.OnPlaylistItemClickedListener

class CurrentPlaylistAdapter(private val tracks: List<Track>) :
    RecyclerView.Adapter<PlaylistViewHolder>() {
    private lateinit var listener: OnPlaylistItemClickedListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            ItemCurrentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.binding.root.setOnClickListener {
            listener.onTrackSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
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
}
