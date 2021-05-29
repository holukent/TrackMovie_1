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
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        //app:deaultNavHost = "true"
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navcontroller = navHostFragment.navController

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.navBottonBar_Movie -> {
                    viewModel.navigationBottomBar(it.itemId,this)
                }
                R.id.navBottonBar_Tv -> {
                    viewModel.navigationBottomBar(it.itemId,this)
                }
                R.id.navBottonBar_Search -> {
                    viewModel.navigationBottomBar(it.itemId,this)
//                    navcontroller.navigate(R.id.searchFragment)
                }
            }
            true
        }
        binding.bottomNavigation.setOnNavigationItemReselectedListener {

        }

//        binding.editSearch.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                viewModel.editInput(v.text)
//            }
//            false
//        }

        //appbarbackupkey
        setupActionBarWithNavController(navcontroller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navcontroller.navigateUp()
    }


//    fun openDb(): TmdbDataBase {
//        val db=  Room.databaseBuilder(
//            applicationContext,
//            TmdbDataBase::class.java,
//            "database-Tmdb"
//        ).build()
//
//        val movieDao = db.movieDao()
//        movieDao.insertAll()
//        return db
//    }
}