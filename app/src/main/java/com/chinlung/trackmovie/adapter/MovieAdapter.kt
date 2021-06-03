package com.chinlung.trackmovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.fragment.MovieFragmentDirections
import com.chinlung.trackmovie.R

import com.chinlung.trackmovie.model.TotalJson
import com.chinlung.trackmovie.viewmodel.ViewModels

class MovieAdapter(
    private val context: Context,
    private val totaljson: TotalJson,
    val viewModel: ViewModels
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterLayoutView =
            LayoutInflater.from(context).inflate(R.layout.list_poster, parent, false)
        return MovieViewHolder(adapterLayoutView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${totaljson.results[position].poster_path}")
            .into(holder.moviePoster)
        holder.itemView.setOnClickListener {
            val action = MovieFragmentDirections.actionMovieFragmentToInfoFragment(
                position = position,
                totalJson = totaljson
            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return totaljson.results.size
    }
}