package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemTrackBinding
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.track.Track

class TrendingAdapter(
    val listener: (track: Track) -> Unit
) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {
    private var tracks = listOf<Track>()

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
        holder.bind(tracks[position], num)
    }

    override fun getItemCount(): Int = tracks.size

    inner class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track, position: String) {
            binding.track = track
            binding.position = position
            binding.container.setOnClickListener {
                listener(track)
            }
        }
    }
}