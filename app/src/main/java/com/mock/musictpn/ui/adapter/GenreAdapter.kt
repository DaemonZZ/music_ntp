package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mock.musictpn.databinding.ItemAlbumBinding
import com.mock.musictpn.databinding.ItemGenreBinding
import com.mock.musictpn.model.album.Album
import com.mock.musictpn.model.genre.Genre

class GenreAdapter(
    val listener: (genre: Genre) -> Unit
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var genres = listOf<Genre>()

    fun setData(newList: List<Genre>) {
        genres = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int = genres.size

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genre = genre
            binding.container.setOnClickListener {
                listener(genre)
            }
        }
    }
}