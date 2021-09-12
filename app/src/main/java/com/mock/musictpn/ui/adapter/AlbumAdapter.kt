package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemAlbumBinding
import com.mock.musictpn.model.album.Album

class AlbumAdapter(
    val listener: (album: Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    private var albums = listOf<Album>()

    fun setData(newList: List<Album>) {
        albums = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    inner class ViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.container.setOnClickListener {
                listener(album)
            }
        }
    }
}