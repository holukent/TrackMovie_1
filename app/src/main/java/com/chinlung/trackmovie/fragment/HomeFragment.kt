package com.chinlung.trackmovie.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.adapter.HomeMovieAdapter
import com.chinlung.trackmovie.adapter.HomeTvAdapter
import com.chinlung.trackmovie.databinding.FragmentHomeBinding
import com.chinlung.trackmovie.model.Result
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    lateinit var adapter:HomeMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = HomeMovieAdapter(viewModel,onitemclick)
        binding.homeRecyclerMovie.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("savedInstanceState","$savedInstanceState")


        if (viewModel.getState(MainActivity.MOVIE_STATE) == null)
            viewModel.requestTmdbApi(TmdbApi.TMDB_MOVIE_HOT)

        if (viewModel.getState(MainActivity.TV_STATE) == null)
            viewModel.requestTmdbApi(TmdbApi.TMDB_TV_HOT)


        viewModel.tvList.observe(viewLifecycleOwner) {
            Log.d("tvlist", "$it")
            setRecycler(binding.homeRecyclerTv, HomeTvAdapter(viewModel, it))
            if (viewModel.getState(MainActivity.TV_STATE) != null) {
                binding.homeRecyclerTv.layoutManager!!.onRestoreInstanceState(
                    viewModel.getState(MainActivity.TV_STATE)
                )
            }
        }
        viewModel.movieList.observe(viewLifecycleOwner) {
//            setRecycler(binding.homeRecyclerMovie, HomeMovieAdapter(viewModel, it,onitemclick))

            adapter.submitList(it)
            binding.homeRecyclerMovie.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.homeRecyclerMovie.setHasFixedSize(true)
            if (viewModel.getState(MainActivity.MOVIE_STATE) != null) {
                binding.homeRecyclerMovie.layoutManager!!.onRestoreInstanceState(
                    viewModel.getState(MainActivity.MOVIE_STATE)
                )
            }
        }


        lifecycleScope.launch {
            viewModel.data().collect {
                Log.d("viewModel.data()","$it")
            }
        }

    }

    private fun <T> setRecycler(recyclerMovie: RecyclerView, adapter: T) {

        recyclerMovie.adapter =
            if (adapter is HomeMovieAdapter) adapter else adapter as HomeTvAdapter
        recyclerMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerMovie.setHasFixedSize(true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveState(
            MainActivity.MOVIE_STATE,
            binding.homeRecyclerMovie.layoutManager?.onSaveInstanceState()!!
        )
        viewModel.saveState(
            MainActivity.TV_STATE,
            binding.homeRecyclerTv.layoutManager?.onSaveInstanceState()!!
        )
    }

    var newlist = mutableListOf<Result>()

    private val onitemclick:(Int,List<Result>,v:View)->Unit = { position, currenlist,v ->

        val state = binding.homeRecyclerMovie.layoutManager?.onSaveInstanceState()

        viewModel.expand(currenlist,position)
        binding.homeRecyclerMovie.layoutManager?.onRestoreInstanceState(state)

        binding.homeRecyclerMovie.layoutManager?.scrollToPosition(position)


//        if (adapter.currentList[position].expand == View.GONE) {
//            adapter.currentList[position].expand = View.VISIBLE
//        }else{
//            adapter.currentList[position].expand = View.GONE
//        }
//        adapter.notifyItemChanged(position)
//        binding.homeRecyclerMovie.setHasFixedSize(true)
//        binding.homeRecyclerMovie.layoutManager?.scrollToPosition(position)








    }
}