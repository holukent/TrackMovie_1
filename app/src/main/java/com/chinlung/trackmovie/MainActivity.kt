package com.chinlung.trackmovie

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.core.view.DisplayCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chinlung.trackmovie.databinding.ActivityMainBinding
//import com.chinlung.trackmovie.viewmodel.ViewModelFactory
import com.chinlung.trackmovie.viewmodel.ViewModels
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
//    private val viewModel: ViewModels by viewModels{
//        ViewModelFactory(
//            (application as MyApplication).container.db.movieDao(),
////            TmdbDataBase.getDatabase(applicationContext).movieDao(),
//        this, null)
//    }

    val viewModel: ViewModels  by stateViewModel()

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

//        lifecycleScope.launch {
//            viewModel.dbGetAll(viewModel.openDb(this@MainActivity))
//        }

        viewModel.dblist.observe(this) {
            Log.d("dblist","$it")
        }

        //app:deaultNavHost = "true"
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navBottonBar_Search -> {
                    navController.navigate(R.id.searchFragment)
                    viewModel.editInput("")
                    binding.snackbar()
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
//        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp() || navController.navigateUp()
        return super.onSupportNavigateUp()
    }

    fun ActivityMainBinding.snackbar(){
        val snackbar = Snackbar.make(this.rootCC, "Snackbar test", Snackbar.LENGTH_SHORT)
        val sbView = snackbar.view
        val textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.text = "123123123"
        snackbar.show()

//        val snackBar = Snackbar.make(rootCC,"123123123", Snackbar.LENGTH_SHORT)
//        val snackbarView = snackBar.view.also {
//            it.setBackgroundColor(0xffffff)
//        }
//        val textView = snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
//
//
////        snackbarView.background = GradientDrawable().also {
////            it.cornerRadius = 80F
////            it.colors = intArrayOf(0xff123456.toInt(),0xff123456.toInt())
////        }
//        val textView1 = TextView(this.root.context)
//        ConstraintLayout(this.root.context).also {
//            (snackbarView as FrameLayout).addView(it)
//            it.background = GradientDrawable().also {
//                it.setColor(0xfF000000.toInt())
//                it.cornerRadius = 80F
//            }
//            it.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//            it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//            it.addView(textView1)
//        }
//        textView1.apply {
//            text = "qweqweqweqwe"
//            textSize = 20f
//        }.updateLayoutParams<ConstraintLayout.LayoutParams> {
//            startToStart = ConstraintLayout.LayoutParams.PARENT_ID
//            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
//            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
//            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
//        }
//
////        (textView.parent as LinearLayout).apply {
////            addView(constartinLayout)
////            background = GradientDrawable().also {
////                it.setColor(0xff654123.toInt())
////                it.cornerRadius = 80F
////            }
////        }
//
//
//        snackbarView.updateLayoutParams<FrameLayout.LayoutParams> {
//            gravity = Gravity.CENTER or Gravity.BOTTOM
//            width = 500
//        }

//        snackBar.show()
    }

    override fun onResume() {
        super.onResume()
        display()
    }

}




class SnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

}

@Preview(showBackground = true)
@Composable
fun Test(){

}

fun Context.display() {
    display?.let {
        DisplayCompat.getSupportedModes(this, display!!)
    }?.toList()?.forEach {
        Log.d("TAG1", "display: ${it.physicalHeight}")
        Log.d("TAG2", "display: ${it.physicalWidth}")
    }
    DisplayCompat.getMode(this, display!!).run {
        Log.d("TAG3", "display: ${physicalWidth}")
        Log.d("TAG4", "display: ${physicalHeight}")
    }

    Resources.getSystem().displayMetrics.apply {
        Log.d("TAG5", "display: ${this}")
    }
}