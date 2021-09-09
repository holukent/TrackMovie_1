package com.chinlung.trackmovie

import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.room.entity.Movie
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@BindingAdapter("imageUrl")
fun ImageView.setBindingImage(url:String? ) {
    Glide.with(this.context)
        .load(url ?: "")
        .into(this)
}
