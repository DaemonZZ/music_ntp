package com.mock.musictpn.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mock.musictpn.R
import com.mock.musictpn.mediaplayer.MusicPlayer

@BindingAdapter("set_image")
fun AppCompatImageView.getImageFromUrl(url: String?) {
    url?.let {
        if(url.contains(MusicPlayer.CONTENT_LOCAL)){
            setBackgroundResource(R.drawable.logo_black)
        }
        else {
            Glide.with(this).load(it)
                .error(R.drawable.logo_black)
                .placeholder(R.drawable.logo_black)
                .into(this)
        }
    }



}