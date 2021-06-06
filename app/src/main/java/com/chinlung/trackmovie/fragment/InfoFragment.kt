package com.chinlung.trackmovie.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.databinding.FragmentInfoBinding
import com.chinlung.trackmovie.viewmodel.ViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView

class InfoFragment : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    private lateinit var binding: FragmentInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        viewModel.jsonn.observe(viewLifecycleOwner){
            viewModel.setImageUrl()
            viewModel.setGenres()

        }


        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)!!.visibility =
            View.GONE
    }


    fun googleInfo() {
        val queryUrl: Uri =
            Uri.parse(
                "${ViewModels.CHROME_SEARCH_PREFIX}${
                    viewModel.jsonn.value?.title ?: ""
                }"
            )
        requireContext().startActivity(Intent(Intent.ACTION_VIEW, queryUrl))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cleanImageUrl()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)!!.visibility =
            View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        binding.infoPoster.setImageDrawable(null)
    }


}