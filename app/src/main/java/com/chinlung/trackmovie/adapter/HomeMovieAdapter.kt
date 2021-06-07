package com.chinlung.trackmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.databinding.ListHomeMovieBinding
import com.chinlung.trackmovie.fragment.HomeFragmentDirections
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class HomeMovieAdapter(
    val viewModel: ViewModels,
    val results: List<Result>
) : RecyclerView.Adapter<HomeMovieAdapter.ItemHolderView>() {

    inner class ItemHolderView(val binding: ListHomeMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListHomeMovieBinding.inflate(inflater, parent, false)
        return ItemHolderView(binding)
    }

    override fun onBindViewHolder(holder: ItemHolderView, position: Int) {
        holder.bind(results[position])

        viewModel.setImage(
            holder.binding.imgHomePoster,
            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}"
        )


//
        holder.itemView.setOnClickListener {
            viewModel.parseId("${results[position].id}","movie")
            val action = HomeFragmentDirections.actionHomeFragmentToInfoFragment(
                title = results[position].title

            )
            it.findNavController().navigate(action)
        }

    }

    override fun getItemCount() = results.size
}