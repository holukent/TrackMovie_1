package com.chinlung.trackmovie.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.databinding.ListSearchResultBinding
import com.chinlung.trackmovie.fragment.SearchFragmentDirections
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class SearchAdapter(
    val context: Context,
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
        val binding = ListSearchResultBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(results[position])


        viewModel.setImage(
            holder.binding.searchPoster,
            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}"
        )
        holder.itemView.setOnClickListener {
            viewModel.parseId("${results[position].id}")
            val action = SearchFragmentDirections.actionSearchFragmentToInfoFragment(
                title = results[position].title
            )
            it.findNavController().navigate(action)
        }

        holder.binding.searchAddWatchList.setOnClickListener {
            val db = viewModel.openDb(context)
            viewModel.dbInsert(
                db,
                results[position].id.toString(),
                results[position].poster_path,
                results[position].title,
                viewModel.tabLayoutItem.value!!.first
            )


        }


        //        holder.binding.icon.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                Log.d("icon", "$isChecked")
//
//            } else {
//                Log.d("icon", "$isChecked")
//            }
//        }
    }

    override fun getItemCount(): Int {
        return results.size
    }
}