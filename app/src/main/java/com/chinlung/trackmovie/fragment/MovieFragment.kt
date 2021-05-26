package com.chinlung.trackmovie.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.chinlung.trackmovie.data.MovieJson
import com.chinlung.trackmovie.databinding.FragmentMovieBinding
import com.chinlung.trackmovie.viewmodel.ViewModels


class MovieFragment : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    private var binding: FragmentMovieBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie,container,false)
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tmdbValue.observe(viewLifecycleOwner) {
            viewModel.requestTMDB(requireContext(), binding!!.recyclerMovie, it)
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}