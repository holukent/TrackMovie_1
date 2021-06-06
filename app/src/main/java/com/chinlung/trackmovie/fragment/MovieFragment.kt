package com.chinlung.trackmovie.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.MainActivity
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

//        viewModel.requestTmdbApi(TmdbApi.TMDB_MOVIE_HOT)
//
//        viewModel.json.observe(viewLifecycleOwner) {
//            setRecycler(binding.recyclerMovie)
//
//            if (viewModel.getState(MainActivity.MOVIE_STATE) != null) {
//                binding.recyclerMovie.layoutManager?.onRestoreInstanceState(
//                    viewModel.getState(MainActivity.MOVIE_STATE)
//                )
//            }
//        }
    }

    private fun setRecycler(recyclerMovie: RecyclerView) {
        recyclerMovie.adapter = MovieAdapter(viewModel,viewModel.json.value!!.results)
        recyclerMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerMovie.setHasFixedSize(true)
    }

    override fun onPause() {
        super.onPause()
//        viewModel.saveState(
//            MainActivity.MOVIE_STATE,
//            binding.recyclerMovie.layoutManager?.onSaveInstanceState()!!
//        )
    }
}