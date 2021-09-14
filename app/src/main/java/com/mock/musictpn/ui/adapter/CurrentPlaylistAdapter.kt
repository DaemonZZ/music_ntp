package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemCurentListBinding
import com.mock.musictpn.databinding.ItemTrackBinding
import com.mock.musictpn.model.track.Track

class CurrentPlaylistAdapter(private val tracks:List<Track>):RecyclerView.Adapter<PlaylistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(ItemCurentListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}

class PlaylistViewHolder(val binding:ItemCurentListBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(track: Track){
        binding.track = track
    }
}
