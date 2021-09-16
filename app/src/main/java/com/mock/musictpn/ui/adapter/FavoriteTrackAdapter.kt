package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.R
import com.mock.musictpn.databinding.ItemFavoriteTrackBinding
import com.mock.musictpn.databinding.ItemTrackByTypeBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.utils.getImageFromUrl

class FavoriteTrackAdapter(
    val listener: (tracks: TrackList) -> Unit
) : ListAdapter<Track, FavoriteTrackAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val trackList = TrackList(currentList,position)
        holder.binding.root.setOnClickListener { listener(trackList) }
    }

    inner class ViewHolder(val binding: ItemFavoriteTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.track = track
            if (track.artistId.isNullOrBlank()){
                binding.imvArtist.setImageResource(R.drawable.logo)
            } else {
                binding.imvArtist.getImageFromUrl(track.getImageUrl())
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }
}