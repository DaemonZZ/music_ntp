package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mock.musictpn.databinding.SliderItemBinding
import com.mock.musictpn.model.album.Album
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val albums: List<Album>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun getCount(): Int = albums.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        return SliderViewHolder(
            SliderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.bind(albums[position])
    }

    class SliderViewHolder(private val binding: SliderItemBinding) : ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
        }
    }
}