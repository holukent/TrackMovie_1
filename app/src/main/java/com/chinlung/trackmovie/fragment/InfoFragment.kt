package com.chinlung.trackmovie.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.databinding.FragmentInfoBinding

import com.chinlung.trackmovie.model.TotalJson
import com.chinlung.trackmovie.model.TvJson
import com.chinlung.trackmovie.viewmodel.ViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView

class InfoFragment : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    private lateinit var binding: FragmentInfoBinding


    private var position: Int = 0

    private var tvjson: TvJson? = null
    private var json: TotalJson? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            position = it!!.getInt("position")

            tvjson = it.getParcelable("tvjson")
            json = it.getParcelable("totalJson")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.infoOverview.movementMethod = ScrollingMovementMethod.getInstance()

        binding.apply {
            viewModeles = viewModel
            infoFragment = this@InfoFragment
            lifecycleOwner = viewLifecycleOwner
        }


        viewModel.setinfo(requireContext(),binding.infoPoster,position,json)
//        if (moviejson != null) {
//            viewModel.setinfo(requireContext(),binding.infoPoster,position,moviejson)
//        }else {
//            viewModel.setinfo(requireContext(),binding.infoPoster,position,tvjson)
//        }

        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)!!.visibility =
            View.GONE
    }

    fun searchToast() {
        val queryUrl: Uri =
            Uri.parse("${ViewModels.CHROME_SEARCH_PREFIX}${viewModel.titleName.value}")
        requireContext().startActivity(Intent(Intent.ACTION_VIEW, queryUrl))
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)!!.visibility =
            View.VISIBLE
    }
}