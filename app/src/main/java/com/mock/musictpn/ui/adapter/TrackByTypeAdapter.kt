package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemTrackByTypeBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.adapter.listener.OnTrackItemClickedListener

class TrackByTypeAdapter : ListAdapter<Track, TrackByTypeAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var listener: OnTrackItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTrackByTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        val trackList = TrackList(currentList)
        trackList.pivot = position
        holder.binding.root.setOnClickListener { listener.onClick(trackList) }
    }
    fun setOnTrackItemClickedListener(listener: OnTrackItemClickedListener){
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemTrackByTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.track = track

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