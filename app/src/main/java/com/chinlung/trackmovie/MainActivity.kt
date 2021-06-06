package com.chinlung.trackmovie

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.chinlung.trackmovie.databinding.ActivityMainBinding
import com.chinlung.trackmovie.viewmodel.ViewModels


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: ViewModels by viewModels()
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TABLE_TV = "tv_table"
        const val TABLE_MOVIE = "movie_table"
        const val DATABASE_TMDB = "dataBaseTMDB"
        const val MOVIE_STATE = "movie"
        const val TV_STATE = "tv"
        const val HOME_STATE = "home"
        const val SEARCH_STATE = "search"
        const val WATCHLIST_STATE = "watchlist"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        //app:deaultNavHost = "true"
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navBottonBar_Search -> {
                    navController.navigate(R.id.searchFragment)
                }
                R.id.navBottonBar_WatchList -> {
                    navController.navigate(R.id.watchList)
                }
                R.id.navBottonBar_Home -> {
                    navController.navigate(R.id.homeFragment)
                }
//                R.id.navBottonBar_Movie -> {
//                    navController.navigate(R.id.movieFragment)
//                }
//                R.id.navBottonBar_Tv -> {
//                    navController.navigate(R.id.tvFragment)
//                }
            }
            viewModel.getNavBarItem(it.itemId)
            true
        }
        binding.bottomNavigation.setOnNavigationItemReselectedListener {}

        //appbarbackupkey
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
        return super.onSupportNavigateUp()
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