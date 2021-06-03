package com.chinlung.trackmovie.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinlung.trackmovie.adapter.MovieAdapter
import com.chinlung.trackmovie.databinding.FragmentMovieBinding
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels


class MovieFragment : Fragment() {


    private val viewModel: ViewModels by activityViewModels()
    private lateinit var binding: FragmentMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie,container,false)
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.requestTmdbApi(TmdbApi.TMDB_MOVIE_HOT)

        viewModel.json.observe(viewLifecycleOwner) {
            Log.d("movie","here")

            binding.recyclerMovie.adapter =  MovieAdapter(viewModel)
            binding.recyclerMovie.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerMovie.setHasFixedSize(true)
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("recyclerlife", "onpause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("recyclerlife", "onstop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("recyclerlife", "ondestroy")
    }
}