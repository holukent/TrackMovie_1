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
import com.chinlung.trackmovie.databinding.ListPosterBinding
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class MovieAdapter(
    val viewModel: ViewModels,
    val results: List<Result>
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ListPosterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
//        val moviePoster: ImageView = itemView.findViewById(R.id.poster)
////        val button: Button = itemView.findViewById(R.id.btn_test)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListPosterBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(results[position])
        viewModel.setImage(
            holder.binding.poster,
            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}"
        )

//        holder.itemView.setOnClickListener {
//            viewModel.getPosition(position)
//            val action = MovieFragmentDirections.actionMovieFragmentToInfoFragment()
//            it.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return viewModel.json.value!!.results.size
    }
}