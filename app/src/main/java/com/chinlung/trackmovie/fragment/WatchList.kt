package com.chinlung.trackmovie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.chinlung.trackmovie.adapter.WatchListAdapter
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
        viewModel.dbGetAll(viewModel.openDb(requireContext()))

        viewModel.dblist.observe(viewLifecycleOwner) {
            binding.recyclerViewWatchlist.adapter = WatchListAdapter(viewModel, it)
            binding.recyclerViewWatchlist.layoutManager = GridLayoutManager(context, 4)
            binding.recyclerViewWatchlist.setHasFixedSize(true)
        }


    }

}