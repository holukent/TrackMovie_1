package com.chinlung.trackmovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.model.TvJson
import com.chinlung.trackmovie.fragment.TvFragmentDirections

class TvAdapter(val context: Context, private val tvgson: TvJson) :
    RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPoster: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val adapterLayoutView =
            LayoutInflater.from(context).inflate(R.layout.list_poster, parent, false)
        return TvViewHolder(adapterLayoutView)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${tvgson.results[position].poster_path}")
            .into(holder.tvPoster)

        holder.itemView.setOnClickListener {
            val action = TvFragmentDirections.actionTvFragmentToInfoFragment(
                position = position,
                tvjson = tvgson
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return tvgson.results.size
    }
}