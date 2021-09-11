package com.mock.musictpn.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mock.musictpn.databinding.SliderItemBinding
import com.mock.musictpn.model.image.Image
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(
    private val listImgs: List<Image>,
    ):SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    override fun getCount(): Int {
        return listImgs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        return SliderViewHolder(SliderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.bind(listImgs[position])
    }

    class SliderViewHolder(val binding:SliderItemBinding):ViewHolder(binding.root){
        fun bind(img:Image){
            binding.img = img
        }
    }
}