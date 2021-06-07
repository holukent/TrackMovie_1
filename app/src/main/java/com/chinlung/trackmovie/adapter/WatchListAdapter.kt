package com.chinlung.trackmovie.adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.databinding.ListWatchlistBinding
import com.chinlung.trackmovie.fragment.WatchListDirections
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.room.entity.Movie
import com.chinlung.trackmovie.viewmodel.ViewModels

class WatchListAdapter(

    val viewModel: ViewModels,
    val results: List<Movie>
) : RecyclerView.Adapter<WatchListAdapter.ItemHolderView>() {

    inner class ItemHolderView(val binding: ListWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Movie) {
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolderView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListWatchlistBinding.inflate(inflater, parent, false)
        return ItemHolderView(binding)
    }

    override fun onBindViewHolder(holder: ItemHolderView, position: Int) {
        holder.bind(results[position])

        viewModel.setImage(holder.binding.imgWatchlist,
            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}")

        holder.itemView.setOnClickListener {
            viewModel.parseId(results[position].movieid,results[position].movieortv)
            val action = WatchListDirections.actionWatchListToInfoFragment(
                title = results[position].title
            )
            it.findNavController().navigate(action)
        }

        holder.itemView.setOnLongClickListener {
                AlertDialog.Builder(holder.itemView.context)
                .setTitle("注意")
                .setMessage("確定刪除?")
                .setPositiveButton("刪除") { _: DialogInterface, _: Int ->
                    val db = viewModel.openDb(holder.itemView.context)
                    viewModel.deleteByid(db,results[position].id)
                }
                .setNegativeButton("取消") { _: DialogInterface, _: Int -> }
                .show()

            true
        }



    }

    override fun getItemCount() = results.size
}