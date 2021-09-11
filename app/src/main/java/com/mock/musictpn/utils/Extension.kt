package com.mock.musictpn.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("load_img")
fun getImageFromUrl(img: ImageView, url:String){
        Glide.with(img).load(url)
//            .error(R.drawable.error)
//            .placeholder(R.drawable.loading)
            .into(img)
}