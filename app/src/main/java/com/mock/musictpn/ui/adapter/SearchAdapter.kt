package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemTrackSearchBinding
import com.mock.musictpn.model.genre.Genre
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList

class SearchAdapter( val listener: (tracks: TrackList) -> Unit) : ListAdapter<Track, SearchAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trackList = TrackList(currentList)
        trackList.pivot = position
        holder.bind(getItem(position), trackList)

    }

    inner class ViewHolder(val binding: ItemTrackSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track, tracks: TrackList) {
            binding.track = track
            binding.container.setOnClickListener {
                listener(tracks)
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