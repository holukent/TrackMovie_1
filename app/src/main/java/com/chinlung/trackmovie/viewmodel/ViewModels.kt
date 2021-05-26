package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
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
import com.bumptech.glide.Glide
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
        const val SEARCH_PREFIX = "https://www.google.com/search?q="
    }

    private var _tmdbValue: MutableLiveData<String> = MutableLiveData()
    val tmdbValue: LiveData<String> get() = _tmdbValue

    private var _fragment: MutableLiveData<Fragment> = MutableLiveData()
    val fragment: LiveData<Fragment> get() = _fragment

    private var _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String> get() = _toast

    private var _movieJson: MutableLiveData<MovieJson> = MutableLiveData()
    val movieJson get() = _movieJson

    private var _tvJson: MutableLiveData<TvJson> = MutableLiveData()
    val tvJson: LiveData<TvJson> get() = _tvJson

    private var _titleName: MutableLiveData<String> = MutableLiveData()
    val titleName: LiveData<String> get() = _titleName

    private var _overview: MutableLiveData<String> = MutableLiveData()
    val overview: LiveData<String> get() = _overview

    private var _releaseDate: MutableLiveData<String> = MutableLiveData()
    val releaseDate: LiveData<String> get() = _releaseDate

    private var _imagePath: MutableLiveData<String> = MutableLiveData()
    val imagePath: LiveData<String> get() = _imagePath

    private var _popularity: MutableLiveData<String> = MutableLiveData()
    val popularity: LiveData<String> get() = _popularity

    private var _voteAverage: MutableLiveData<String> = MutableLiveData()
    val voteAverage: LiveData<String> get() = _voteAverage

    private var _voteCount: MutableLiveData<String> = MutableLiveData()
    val voteCount : LiveData<String> get() = _voteCount


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
                            _movieJson.value = Gson().fromJson(it, MovieJson::class.java)
                            recyclerView.adapter = MovieAdapter(context, movieJson.value!!)
                            recyclerView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            recyclerView.setHasFixedSize(true)
                        }
                        TMDB_TV_HOT -> {
                            _tvJson.value = Gson().fromJson(it, TvJson::class.java)
                            recyclerView.adapter = TvAdapter(context, tvJson.value!!)
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

    fun requestApi(context: Context, movieortv: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val queue = Volley.newRequestQueue(context)
            val stringrequest = StringRequest(
                Request.Method.GET,
                if (movieortv == "movie") TMDB_MOVIE_HOT else TMDB_TV_HOT,
                {
                    if (movieortv == "movie") {
                        _movieJson.value = Gson().fromJson(it, MovieJson::class.java)
                    } else {
                        _tvJson.value = Gson().fromJson(it, TvJson::class.java)
                    }
                },
                {}
            )
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

    fun setinfo(
        context: Context,
        movieortv: String,
        infoPoster: ImageView,
        position: Int,
    ) {
        if (movieortv == "movie") {
            _releaseDate.value = movieJson.value!!.results[position].release_date
            _overview.value = movieJson.value!!.results[position].overview
            _imagePath.value = movieJson.value!!.results[position].poster_path
            _titleName.value = movieJson.value!!.results[position].title
            _popularity.value = movieJson.value!!.results[position].popularity.toString()
            _voteAverage.value = movieJson.value!!.results[position].vote_average.toString()
            _voteCount.value = movieJson.value!!.results[position].vote_count.toString()
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${imagePath.value}")
                .into(infoPoster)

        } else if (movieortv == "tv") {
            _releaseDate.value = tvJson.value!!.results[position].first_air_date
            _overview.value = tvJson.value!!.results[position].overview
            _imagePath.value = tvJson.value!!.results[position].poster_path
            _titleName.value = tvJson.value!!.results[position].name
            _popularity.value = tvJson.value!!.results[position].popularity.toString()
            _voteAverage.value = tvJson.value!!.results[position].vote_average.toString()
            _voteCount.value = tvJson.value!!.results[position].vote_count.toString()

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${imagePath.value}")
                .into(infoPoster)
        }
    }


//    private fun RecyclerView.setRecyclerAdapter(context: Context, gson: MovieJson) {
//        this.adapter = MovieAdapter(context, gson)
//        this.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        //                this.layoutManager = GridLayoutManager(context, 2)
//        this.setHasFixedSize(true)
//    }
//

}