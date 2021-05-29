package com.chinlung.trackmovie.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.fragment.MovieFragmentDirections
import com.chinlung.trackmovie.model.MovieJson
import com.chinlung.trackmovie.model.SearchKeyWordJson

class SearchAdapter(
    val context: Context,
    private val moviegson: SearchKeyWordJson,

    ) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.search_poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val adapterLayoutView =
            LayoutInflater.from(context).inflate(R.layout.list_search_result, parent, false)
        return SearchViewHolder(adapterLayoutView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w92${moviegson.results[position].poster_path}")
            .into(holder.moviePoster)
//        holder.itemView.setOnClickListener {
//            val action = MovieFragmentDirections.actionMovieFragmentToInfoFragment(
//                position = position,
//                titleName = moviegson.results[position].title,
//                movieortv = "movie"
//            )
//            it.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return moviegson.results.size
    }
}