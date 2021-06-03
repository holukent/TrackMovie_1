package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.model.*
import com.chinlung.trackmovie.repository.TmdbApi
import com.chinlung.trackmovie.room.entity.Movie
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.*


class ViewModels : ViewModel() {

    companion object {
        const val CHROME_SEARCH_PREFIX = "https://www.google.com/search?q="
    }



    private val _tvJson: MutableLiveData<TvJson> = MutableLiveData()
    val tvJson: LiveData<TvJson> get() = _tvJson

    private var _json: MutableLiveData<TotalJson> = MutableLiveData()
    val json: LiveData<TotalJson> get() = _json

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

    private val _dataBase: MutableLiveData<List<Movie>> = MutableLiveData()
    val dataBase: LiveData<List<Movie>> get() = _dataBase

    private var _tabLayoutItem: MutableLiveData<String> = MutableLiveData()
    val tabLayoutItem: LiveData<String> get() = _tabLayoutItem

    private var _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> get() = _url


    fun setInfo() {

    }

    fun <T> setinfo(
        context: Context,
        infoPoster: ImageView,
        position: Int,
        pjson: T
    ) {

        if (pjson is TotalJson) {
            Log.d("totaljson","${json.value!!.results[position]}")
            _releaseDate.value = json.value!!.results[position].release_date
            _overview.value = json.value!!.results[position].overview
            _imagePath.value = json.value!!.results[position].poster_path
            _titleName.value = json.value!!.results[position].title
            _popularity.value = json.value!!.results[position].popularity.toString()
            _voteAverage.value = json.value!!.results[position].vote_average.toString()
            _voteCount.value = json.value!!.results[position].vote_count.toString()
        } else if (pjson is TvJson) {
            _releaseDate.value = tvJson.value!!.results[position].release_date
            _overview.value = tvJson.value!!.results[position].overview
            _imagePath.value = tvJson.value!!.results[position].poster_path
            _titleName.value = tvJson.value!!.results[position].title
            _popularity.value = tvJson.value!!.results[position].popularity.toString()
            _voteAverage.value = tvJson.value!!.results[position].vote_average.toString()
            _voteCount.value = tvJson.value!!.results[position].vote_count.toString()

        }
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${imagePath.value}")
            .into(infoPoster)
    }

    fun editInput(text: CharSequence) {
        _editInput.value = text.toString()
    }

    fun requestTmdbApi(api: String, editText: String = "") {

        viewModelScope.launch(Dispatchers.IO) {
            val clientBuild = OkHttpClient.Builder().build()
            val requestBuild =
                if (editText == "") {
                    Request.Builder().url(api).build()
                } else {
                    Request.Builder()
                        .url(api).build()
                }
            clientBuild.newCall(requestBuild).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("requset", "Failure")
                }

                override fun onResponse(call: Call, response: Response) {

                    when (api) {
                        TmdbApi.TMDB_TV_HOT -> {
                            _tvJson.postValue(
                                Gson().fromJson(response.body!!.string(), TvJson::class.java)
                            )
                        }

                        else -> {
                            _json.postValue(
                                Gson().fromJson(
                                    response.body!!.string(), TotalJson::class.java
                                )
                            )
                        }
                    }
                }
            })
        }
    }

    fun getTabLayoutItem(tab: TabLayout.Tab) {
        _tabLayoutItem.value = tab.text.toString()
    }

    fun editSearchApi(edit: String): String {
        return if (tabLayoutItem.value == TmdbApi.TV) "${TmdbApi.TMDB_SEARCH}${TmdbApi.TV}${TmdbApi.TMDB_API}&query=$edit"
        else "${TmdbApi.TMDB_SEARCH}${TmdbApi.MOVIE}${TmdbApi.TMDB_API}&query=$edit"

    }
}
