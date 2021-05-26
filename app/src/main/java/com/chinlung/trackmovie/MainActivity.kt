package com.chinlung.trackmovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.chinlung.trackmovie.databinding.ActivityMainBinding
import com.chinlung.trackmovie.viewmodel.ViewModels


class MainActivity : AppCompatActivity() {
    private lateinit var navcontroller: NavController
    private val viewModel: ViewModels by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        //app:deaultNavHost = "true"
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navcontroller = navHostFragment.navController

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            viewModel.changtmdbValue(it.itemId)
            true
        }
        binding.bottomNavigation.setOnNavigationItemReselectedListener {}


//        binding.bottomNavigation.setOnNavigationItemSelectedListener(listenerBottom)
        //appbarbackupkey
        setupActionBarWithNavController(navcontroller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navcontroller.navigateUp()
    }
}