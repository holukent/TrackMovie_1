package com.chinlung.trackmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.fragment.MovieFragmentDirections
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class MovieAdapter(
    val viewModel: ViewModels
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.poster)
        val button: Button = itemView.findViewById(R.id.btn_test)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterLayoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_poster, parent, false)
        return MovieViewHolder(adapterLayoutView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        viewModel.setImage(holder.moviePoster,
            "${TmdbApi.TMDB_IMAGE}${viewModel.json.value!!.results[position].poster_path}")

        holder.itemView.setOnClickListener {
            viewModel.getPosition(position)
            val action = MovieFragmentDirections.actionMovieFragmentToInfoFragment(
                position = position,
//                totalJson = totalJson
            )
            it.findNavController().navigate(action)
        }

        holder.button.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return viewModel.json.value!!.results.size
    }
}