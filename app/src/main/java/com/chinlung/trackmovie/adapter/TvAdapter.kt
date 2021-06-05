package com.chinlung.trackmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.fragment.TvFragmentDirections
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class TvAdapter( val viewModel: ViewModels) :
    RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    class TvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPoster: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val adapterLayoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_poster, parent, false)
        return TvViewHolder(adapterLayoutView)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        viewModel.setImage(
             holder.tvPoster,
            "${TmdbApi.TMDB_IMAGE}${viewModel.json.value!!.results[position].poster_path}"
        )

        holder.itemView.setOnClickListener {
            viewModel.getPosition(position)
            val action = TvFragmentDirections.actionTvFragmentToInfoFragment(
//                position = position,
//                totalJson = totaljson
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return viewModel.json.value!!.results.size
    }
}