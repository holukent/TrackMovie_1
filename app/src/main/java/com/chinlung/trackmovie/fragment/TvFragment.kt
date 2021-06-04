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
import com.chinlung.trackmovie.adapter.TvAdapter
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
        binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.requestTmdbApi(TmdbApi.TMDB_TV_HOT)

        viewModel.json.observe(viewLifecycleOwner) {
            setRecycler(binding.recyclerTv)
            if (viewModel.getstate(MainActivity.TV_STATE) != null){
                binding.recyclerTv.layoutManager!!.onRestoreInstanceState(
                    viewModel.getstate(MainActivity.TV_STATE)
                )
            }

        }
    }

    override fun onPause() {
        viewModel.saveState(
            MainActivity.TV_STATE,
            binding.recyclerTv.layoutManager?.onSaveInstanceState()!!
        )
        super.onPause()
    }
    private fun setRecycler(recyclerMovie: RecyclerView) {
        recyclerMovie.adapter = TvAdapter(viewModel)
        recyclerMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerMovie.setHasFixedSize(true)
    }
}