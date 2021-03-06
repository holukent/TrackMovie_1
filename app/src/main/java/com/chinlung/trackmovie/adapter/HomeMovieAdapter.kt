package com.chinlung.trackmovie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.databinding.ListHomeMovieBinding
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class HomeMovieAdapter(
    val viewModel: ViewModels,
    val onitemclick: (Int,List<Result>,v: View) -> Unit,
//    val results: List<Result>,

//) : RecyclerView.Adapter<HomeMovieAdapter.ItemHolderView>() {
) : ListAdapter<Result,HomeMovieAdapter.ItemHolderView>(DiffUtilCallback){
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
        holder.bind(getItem(position))
        val item = getItem(position)
        holder.binding.textt.visibility = item.expand

        viewModel.setImage(
            holder.binding.imgHomePoster,
//            "${TmdbApi.TMDB_IMAGE}${results[position].poster_path}"
            "${TmdbApi.TMDB_IMAGE}${item.poster_path}"
        )

//
        holder.itemView.setOnClickListener {
//            if (currentList[position].expand == View.GONE) {
//                currentList[position].expand = View.VISIBLE
//            }else{
//                currentList[position].expand = View.GONE
//            }
//            notifyItemChanged(position)
//            submitList(currentList)
//            notifyItemChanged(position)
            onitemclick(position,this.currentList,it)
//            val test = currentList.toMutableList()
//            test[position] = getItem(position).also {
//                it.expand = View.GONE }
//            submitList(test)
//            notifyItemChanged(position)
//            onitemclick(position)

//            viewModel.parseId("${results[position].id}","movie")


//            val action = HomeFragmentDirections.actionHomeFragmentToInfoFragment(
////                title = results[position].title
//            )
//            it.findNavController().navigate(action)
        }

    }



//    override fun getItemCount() = results.size

    object DiffUtilCallback: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
//            return false
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            Log.d("areContentsTheSame","${oldItem.expand} : ${newItem.expand}")
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Result, newItem: Result): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

    }

//    override fun onBindViewHolder(
//        holder: ItemHolderView,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        if(payloads.isEmpty()) {
//            onBindViewHolder(holder,position)
//        }else {
//            val test = payloads[0] as Bundle
//            Log.d("sadgsdg2","${test.get("expand")}")
//
//            holder.binding.imgHomePoster.setImageURI(null)
//        }
//    }
}