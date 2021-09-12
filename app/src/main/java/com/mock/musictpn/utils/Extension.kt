package com.mock.musictpn.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("set_image")
fun AppCompatImageView.getImageFromUrl(url: String?) {
    url?.let {
        Glide.with(this).load(it)
//            .error(R.drawable.error)
//            .placeholder(R.drawable.loading)
            .into(this)
    }

}