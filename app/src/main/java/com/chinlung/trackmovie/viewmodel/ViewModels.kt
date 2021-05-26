package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.adapter.MovieAdapter
import com.chinlung.trackmovie.adapter.TvAdapter
import com.chinlung.trackmovie.data.MovieJson
import com.chinlung.trackmovie.data.TvJson
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModels : ViewModel() {

    companion object {
        const val TMDB_TV_HOT =
            "https://api.themoviedb.org/3/discover/tv?api_key=3295120c2c04434d28ac1d5ff19a3af0&sort_by=popularity.desc"
        const val TMDB_MOVIE_HOT =
            "https://api.themoviedb.org/3/discover/movie?api_key=3295120c2c04434d28ac1d5ff19a3af0&sort_by=popularity.desc"
    }

    private var _tmdbValue: MutableLiveData<String> = MutableLiveData()
    val tmdbValue: LiveData<String> get() = _tmdbValue

    private var _fragment: MutableLiveData<Fragment> = MutableLiveData()
    val fragment: LiveData<Fragment> get() = _fragment

    private var _toast : MutableLiveData<String> = MutableLiveData()
    val toast:LiveData<String> get() = _toast

    init {
        _tmdbValue.value = TMDB_MOVIE_HOT
    }

    fun requestTMDB(context: Context, recyclerView: RecyclerView, json: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val queue = Volley.newRequestQueue(context)
            val stringrequest = StringRequest(
                Request.Method.GET,
                json,
                {
                    when (json) {
                        TMDB_MOVIE_HOT -> {
                            val gson = Gson().fromJson(it, MovieJson::class.java)
                            recyclerView.adapter = MovieAdapter(context, gson)
                            recyclerView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            recyclerView.setHasFixedSize(true)
                        }
                        TMDB_TV_HOT -> {
                            val gson = Gson().fromJson(it, TvJson::class.java)
                            recyclerView.adapter = TvAdapter(context, gson)
                            recyclerView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            recyclerView.setHasFixedSize(true)
                        }
                    }
                },
                { Toast.makeText(context, "wrong", Toast.LENGTH_SHORT).show() })

            queue.add(stringrequest)
        }
    }

    fun changtmdbValue(itemid: Int) {
        when (itemid) {
            R.id.navBottonBar_Movie -> {
                _tmdbValue.value = TMDB_MOVIE_HOT
            }
            R.id.navBottonBar_Tv -> {
                _tmdbValue.value = TMDB_TV_HOT
            }
            R.id.navBottonBar_Search -> {
                _toast.value = "尚未開發"
            }
        }
    }

//    private fun RecyclerView.setRecyclerAdapter(context: Context, gson: MovieJson) {
//        this.adapter = MovieAdapter(context, gson)
//        this.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        //                this.layoutManager = GridLayoutManager(context, 2)
//        this.setHasFixedSize(true)
//    }


}