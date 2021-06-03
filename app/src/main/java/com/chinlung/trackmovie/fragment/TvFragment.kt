package com.chinlung.trackmovie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.adapter.TvAdapter
import com.chinlung.trackmovie.databinding.FragmentMovieBinding
import com.chinlung.trackmovie.databinding.FragmentTvBinding
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.viewmodel.ViewModels

class TvFragment : Fragment() {
    private val viewModel: ViewModels by activityViewModels()
    lateinit var binding: FragmentTvBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.requestTmdbApi(TmdbApi.TMDB_TV_HOT)

        viewModel.tvJson.observe(viewLifecycleOwner) {
            binding.recyclerTv.adapter = TvAdapter(requireContext(),it)
            binding.recyclerTv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerTv.setHasFixedSize(true)
        }
    }
}