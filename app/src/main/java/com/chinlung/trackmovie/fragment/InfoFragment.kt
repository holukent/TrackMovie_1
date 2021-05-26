package com.chinlung.trackmovie.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.chinlung.trackmovie.databinding.FragmentInfoBinding
import com.chinlung.trackmovie.viewmodel.ViewModels

class InfoFragment : Fragment() {

    val viewModel: ViewModels by activityViewModels()
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    lateinit var gson: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            gson = it?.getString("gson").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("gson", gson)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}