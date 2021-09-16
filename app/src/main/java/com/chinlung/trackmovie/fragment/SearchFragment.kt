package com.chinlung.trackmovie.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.adapter.SearchAdapter
import com.chinlung.trackmovie.databinding.FragmentSearchBinding
import com.chinlung.trackmovie.viewmodel.ViewModels
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


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
        binding.editSearch.apply {
            addTextChangedListener {
////                inputType = if (it?.length!! >= 1)TYPE_CLASS_NUMBER else TYPE_TEXT_FLAG_CAP_CHARACTERS
//                inputType = if (it?.length!! < 1) 0x00001001 else TYPE_CLASS_NUMBER
            }
            filters = arrayOf(InputFilter.AllCaps())

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        lifecycleScope.launch{
//            viewModel.edit(binding.editSearch.text.toString()).collect{
//                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
//            }
//        }



        binding.tabLayout.getTabAt(viewModel.tabLayoutItem.value!!.second)!!.select()

        viewModel.editInput.observe(viewLifecycleOwner) {

            if (it != "") viewModel.requestSeachApi(viewModel.editSearchApi(it))
        }

        viewModel.json.observe(viewLifecycleOwner) {
            if (viewModel.editInput.value != "" && viewModel.editInput.value != null) {
                setRecycler(binding.recyclerSearchMovie)
                if (viewModel.getState(MainActivity.SEARCH_STATE) != null) {
                    binding.recyclerSearchMovie.layoutManager!!.onRestoreInstanceState(
                        viewModel.getState(MainActivity.SEARCH_STATE)
                    )
                }
            }

        }

        binding.editSearch.setOnEditorActionListener { v, actionId, event ->
            Log.d("editSearch","${actionId} : $event")
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.editInput(v.text)
                binding.editSearch.text?.clear()
                requireContext().hideKeyBoard(v)
                v.clearFocus()
            }
            true
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.getTabLayoutItem(tab)
                if (viewModel.editInput.value != "" && viewModel.editInput.value != null)
                    viewModel.requestSeachApi(viewModel.editSearchApi(viewModel.editInput.value!!))
                lifecycleScope.launch {
                    viewModel.dbGetAll(viewModel.openDb(requireContext()))
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        lifecycleScope.launch {
            viewModel.data().collect {
                Log.d("viewModel.data()","$it")
            }
        }
    }


    private fun Context.hideKeyBoard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setRecycler(recyclerMovie: RecyclerView) {
        recyclerMovie.adapter =
            SearchAdapter(requireContext(), viewModel, viewModel.json.value!!.results)
        recyclerMovie.setHasFixedSize(true)
    }

    override fun onPause() {
        viewModel.saveState(
            MainActivity.SEARCH_STATE,
            binding.recyclerSearchMovie.layoutManager?.onSaveInstanceState()!!
        )
        super.onPause()
    }

    fun getedit():String {
        return binding.editSearch.text.toString()
    }
}