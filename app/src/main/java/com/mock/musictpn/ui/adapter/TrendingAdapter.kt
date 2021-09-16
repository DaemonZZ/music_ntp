package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemTrackBinding
import com.mock.musictpn.model.track.Track
import com.mock.musictpn.model.track.TrackList
import com.mock.musictpn.ui.adapter.listener.OnTrackItemClickedListener

class TrendingAdapter(
) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {
    private var tracks = listOf<Track>()
    private lateinit var listener: OnTrackItemClickedListener
    fun setData(newList: List<Track>) {
        tracks = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val num = (position + 1).toString()
        val trackList = TrackList(tracks,position)
        holder.bind(tracks[position], num)
        holder.binding.root.setOnClickListener { listener.onClick(trackList) }
    }

    override fun getItemCount(): Int = tracks.size

    fun setOnTrackItemClickedListener(listener: OnTrackItemClickedListener){
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track, position: String) {
            binding.track = track
            binding.position = position
        }
    }

}

