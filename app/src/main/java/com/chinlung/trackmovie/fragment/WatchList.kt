package com.chinlung.trackmovie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.databinding.FragmentMovieBinding
import com.chinlung.trackmovie.databinding.FragmentSearchBinding
import com.chinlung.trackmovie.databinding.FragmentWatchListBinding
import com.chinlung.trackmovie.viewmodel.ViewModels

class WatchList : Fragment() {
    val viewModel: ViewModels by activityViewModels()
    lateinit var binding:FragmentWatchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

}