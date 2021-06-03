package com.chinlung.trackmovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chinlung.trackmovie.databinding.ActivityMainBinding
import com.chinlung.trackmovie.viewmodel.ViewModels


class MainActivity : AppCompatActivity() {
    private lateinit var navcontroller: NavController
    private val viewModel: ViewModels by viewModels()
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TABLE_TV = "tv_table"
        const val TABLE_MOVIE = "movie_table"
        const val DATABASE_TMDB = "dataBaseTMDB"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        //app:deaultNavHost = "true"
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navcontroller = navHostFragment.navController


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navBottonBar_Search ->{
                    navcontroller.navigate(R.id.searchFragment)
                }
                R.id.navBottonBar_WatchList-> {
                    navcontroller.navigate(R.id.watchList)
                }
                R.id.navBottonBar_Movie-> {
                    navcontroller.navigate(R.id.movieFragment)
                }
                R.id.navBottonBar_Tv -> {
                    navcontroller.navigate(R.id.tvFragment)
                }
            }
            viewModel.getNavBarItem(it.itemId)
            true
        }
        binding.bottomNavigation.setOnNavigationItemReselectedListener {}

        //appbarbackupkey
//        setupActionBarWithNavController(navcontroller)
    }

    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp() || navcontroller.navigateUp()
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