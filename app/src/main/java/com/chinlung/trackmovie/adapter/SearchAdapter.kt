package com.chinlung.trackmovie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class SearchAdapter(
    val viewModel: ViewModels
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchimg: ImageView = itemView.findViewById(R.id.search_poster)
        val searchTitle: TextView = itemView.findViewById(R.id.search_titleName)
        val searchrelease: TextView = itemView.findViewById(R.id.search_releaseDate)

        val icon: CheckBox = itemView.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val adapterLayoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_search_result, parent, false)
        return SearchViewHolder(adapterLayoutView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.icon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("icon", "$isChecked")

            } else {
                Log.d("icon", "$isChecked")
            }
        }
        viewModel.setImage(holder.searchimg,
            "${TmdbApi.TMDB_IMAGE}${viewModel.json.value!!.results[position].poster_path}")

        holder.searchTitle.text = viewModel.json.value!!.results[position].title
        holder.searchrelease.text = "發佈日期: ${viewModel.json.value!!.results[position].release_date}"
        holder.itemView.setOnClickListener {    }
    }

    override fun getItemCount(): Int {
        return viewModel.json.value!!.results.size
    }
}