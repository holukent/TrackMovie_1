package com.chinlung.trackmovie.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.chinlung.trackmovie.databinding.FragmentMovieBinding
import com.chinlung.trackmovie.viewmodel.ViewModels


class MovieFragment : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tmdbValue.observe(viewLifecycleOwner) {
            viewModel.requestTMDB(requireContext(),binding.recyclerMovie,it)
        }

        viewModel.toast.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}