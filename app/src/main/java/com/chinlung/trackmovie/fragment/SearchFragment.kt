package com.chinlung.trackmovie.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.adapter.SearchAdapter
import com.chinlung.trackmovie.adapter.TvAdapter
import com.chinlung.trackmovie.databinding.FragmentSearchBinding
import com.chinlung.trackmovie.viewmodel.ViewModels
import com.google.android.material.tabs.TabLayout

class SearchFragment : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    lateinit var binding: FragmentSearchBinding

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
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.editInput.observe(viewLifecycleOwner) {
            viewModel.editSearchApi(it)
            if(it != "") viewModel.requestTmdbApi()
        }

        viewModel.json.observe(viewLifecycleOwner) {
            if (viewModel.editInput.value != "" && viewModel.editInput.value != null) {
                setRecycler(binding.recyclerSearch)
                if(viewModel.getstate(MainActivity.SEARCH_STATE) != null){
                    binding.recyclerSearch.layoutManager!!.onRestoreInstanceState(
                        viewModel.getstate(MainActivity.SEARCH_STATE)
                    )
                }
            }

        }

        binding.editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.editInput(v.text)
                binding.editSearch.text?.clear()
                requireContext().hideKeyBoard(v)
                v.clearFocus()
            }
            true
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.getTabLayoutItem(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }


    private fun Context.hideKeyBoard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setRecycler(recyclerMovie: RecyclerView) {
        recyclerMovie.adapter = SearchAdapter(viewModel)
        recyclerMovie.setHasFixedSize(true)
    }

    override fun onPause() {
        viewModel.saveState(MainActivity.SEARCH_STATE,
            binding.recyclerSearch.layoutManager?.onSaveInstanceState()!!)
        super.onPause()
    }
}