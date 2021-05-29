package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.BuildConfig
import com.chinlung.trackmovie.R
import com.chinlung.trackmovie.model.MovieJson
import com.chinlung.trackmovie.model.SearchKeyWordJson
import com.chinlung.trackmovie.model.TvJson
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModels : ViewModel() {

    companion object {
        const val TMDB_TV_HOT =
            "https://api.themoviedb.org/3/discover/tv?api_key=${BuildConfig.TMDB_KEY}&sort_by=popularity.desc"
        const val TMDB_MOVIE_HOT =
            "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.TMDB_KEY}&sort_by=popularity.desc"
        const val CHROME_SEARCH_PREFIX = "https://www.google.com/search?q="

        const val SEARCH_TMDB =
            "https://api.themoviedb.org/3/search/movie?api_key=${BuildConfig.TMDB_KEY}&language=en-US&page=1&include_adult=false&query="
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

    private var _searchKeyWordJson: MutableLiveData<SearchKeyWordJson> = MutableLiveData()
    val searchKeyWordJson: LiveData<SearchKeyWordJson> get() = _searchKeyWordJson

    var _gson: MutableLiveData<Any> = MutableLiveData()
    val gson: LiveData<Any> get() = _gson

    private var _titleName: MutableLiveData<String> = MutableLiveData()
    val titleName: LiveData<String> get() = _titleName

    private var _overview: MutableLiveData<String> = MutableLiveData()
    val overview: LiveData<String> get() = _overview

    private var _releaseDate: MutableLiveData<String> = MutableLiveData()
    val releaseDate: LiveData<String> get() = _releaseDate

    private var _imagePath: MutableLiveData<String> = MutableLiveData()
    private val imagePath: LiveData<String> get() = _imagePath

    private var _popularity: MutableLiveData<String> = MutableLiveData()
    val popularity: LiveData<String> get() = _popularity

    private var _voteAverage: MutableLiveData<String> = MutableLiveData()
    val voteAverage: LiveData<String> get() = _voteAverage

    private var _voteCount: MutableLiveData<String> = MutableLiveData()
    val voteCount: LiveData<String> get() = _voteCount

    private var _editInput: MutableLiveData<String> = MutableLiveData()
    val editInput: LiveData<String> get() = _editInput

    private var _navitemid: MutableLiveData<Int> = MutableLiveData()
    val navitemid: LiveData<Int> get() = _navitemid


    init {
        _tmdbValue.value = TMDB_MOVIE_HOT
    }


    fun navigationBottomBar(itemid: Int, requireContext: Context) {
        _navitemid.value = itemid
        when (itemid) {
            R.id.navBottonBar_Movie -> {
                _tmdbValue.value = TMDB_MOVIE_HOT
            }
            R.id.navBottonBar_Tv -> {
                _tmdbValue.value = TMDB_TV_HOT
            }
            R.id.navBottonBar_Search -> {
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


    fun requestapi(
        requireContext: Context,
        tmdbValue: String? = null,
        moviewortv: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val queue = Volley.newRequestQueue(requireContext)
            val stringrequest = StringRequest(
                Request.Method.GET,
                if (tmdbValue == TMDB_MOVIE_HOT || moviewortv == "movie") TMDB_MOVIE_HOT
                else if (tmdbValue == TMDB_TV_HOT || moviewortv == "tv") TMDB_TV_HOT
                else SEARCH_TMDB,
                {
                    if (tmdbValue == TMDB_MOVIE_HOT || moviewortv == "movie") {
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

    fun editInput(text: CharSequence) {
        _editInput.value = text.toString()
    }

    fun requestsearch(requireContext: Context,editText: String) {
        val queue = Volley.newRequestQueue(requireContext)
        val stringrequest = StringRequest(
            Request.Method.GET,
            "${SEARCH_TMDB}$editText",
            {
                _searchKeyWordJson.value = Gson().fromJson(it,SearchKeyWordJson::class.java)
            },
            {}
        )
        queue.add(stringrequest)
    }
}