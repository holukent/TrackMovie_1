package com.chinlung.trackmovie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.databinding.ListHomeTvBinding
import com.chinlung.trackmovie.fragment.HomeFragmentDirections
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class HomeTvAdapter(
    val viewModel: ViewModels,
    val results: List<Result>
) : RecyclerView.Adapter<HomeTvAdapter.ItemHolderView>() {

    inner class ItemHolderView(val binding: ListHomeTvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListHomeTvBinding.inflate(inflater, parent, false)
        return ItemHolderView(binding)
    }

    override fun onBindViewHolder(holder: ItemHolderView, position: Int) {
        holder.bind(results[position])

        viewModel.setImage(
            holder.binding.imgHomePoster,
            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}"
        )

        holder.itemView.setOnClickListener {
            viewModel.parseId("${results[position].id}","tv")
            val action = HomeFragmentDirections.actionHomeFragmentToInfoFragment(
                title = results[position].title
            )
            it.findNavController().navigate(action)
        }

    }
    override fun getItemCount() = results.size
}