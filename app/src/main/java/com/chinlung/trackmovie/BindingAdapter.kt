package com.chinlung.trackmovie

import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.room.entity.Movie

@BindingAdapter("imageUrl")
fun setBindingImage(imageView: ImageView,url:String? ) {
    Glide.with(imageView.context)
        .load(url ?: "")
        .into(imageView)
}
