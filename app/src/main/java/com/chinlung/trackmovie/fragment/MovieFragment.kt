package com.chinlung.trackmovie.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.adapter.MovieAdapter
import com.chinlung.trackmovie.adapter.SearchAdapter
import com.chinlung.trackmovie.adapter.TvAdapter
import com.chinlung.trackmovie.databinding.FragmentMovieBinding
import com.chinlung.trackmovie.model.MovieJson
import com.chinlung.trackmovie.model.SearchKeyWordJson
import com.chinlung.trackmovie.model.TvJson
import com.chinlung.trackmovie.viewmodel.ViewModels


class MovieFragment : Fragment() {


    private val viewModel: ViewModels by activityViewModels()
    private lateinit var binding: FragmentMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment_Recycle", "onCreate: $savedInstanceState")
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


        viewModel.tmdbValue.observe(viewLifecycleOwner) {
            viewModel.requestapi(
                requireContext(),
                tmdbValue = viewModel.tmdbValue.value!!,
                moviewortv = null
            )

        }

        viewModel.movieJson.observe(viewLifecycleOwner) {
            it.setRecyclerView()
        }

        viewModel.tvJson.observe(viewLifecycleOwner) {
            it.setRecyclerView()
        }

        viewModel.searchKeyWordJson.observe(viewLifecycleOwner) {
            binding.recyclerSearch.adapter = SearchAdapter(requireContext(),it)

        }

        viewModel.navitemid.observe(viewLifecycleOwner) {
            if (it == R.id.navBottonBar_Search) {
                binding.recyclerSearch.visibility = View.VISIBLE
                binding.recyclerMovie.visibility = View.GONE

                viewModel.requestsearch(requireContext(),"玩命")

            } else {
                binding.recyclerSearch.visibility = View.GONE
                binding.recyclerMovie.visibility = View.VISIBLE
            }
        }

        viewModel.editInput.observe(viewLifecycleOwner) {
            Log.d("editInput", it)
//            if (it != "") {
//                val json = "${ViewModels.SEARCH_TMDB}/$it"
//                viewModel.searchjsontogson(requireContext(),json)
//            }
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun <T> T.setRecyclerView() {
        binding.recyclerMovie.adapter =
            if (this is MovieJson) MovieAdapter(requireContext(), this)
            else TvAdapter(requireContext(), this as TvJson)
        binding.recyclerMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMovie.setHasFixedSize(true)
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