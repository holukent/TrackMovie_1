package com.chinlung.trackmovie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.databinding.ListSearchResultBinding
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class SearchAdapter(
    val viewModel: ViewModels,
    val results: List<Result>,

    ) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val binding: ListSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListSearchResultBinding.inflate(inflater,parent,false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(results[position])
        viewModel.setImage(
            holder.binding.searchPoster,
            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}"
        )
        holder.binding.icon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("icon", "$isChecked")

            } else {
                Log.d("icon", "$isChecked")
            }
        }

        holder.itemView.setOnClickListener {
            viewModel.getPosition(position)
            it.findNavController().navigate(R.id.infoFragment)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }
}