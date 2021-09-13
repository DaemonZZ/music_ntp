package com.mock.musictpn.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mock.musictpn.R


@BindingAdapter("set_image")
fun AppCompatImageView.getImageFromUrl(url: String?) {
    url?.let {
        Glide.with(this).load(it)
            .error(R.drawable.sky)
            .placeholder(R.drawable.sky)
            .into(this)
    }

}