package com.chinlung.trackmovie.viewmodel

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
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

    private var _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int> = _position

    private var _json: MutableLiveData<TotalJson> = MutableLiveData()
    val json: LiveData<TotalJson> get() = _json

    private var _imagePath: MutableLiveData<String> = MutableLiveData()
    private val imagePath: LiveData<String> get() = _imagePath

    private var _editInput: MutableLiveData<String> = MutableLiveData()
    val editInput: LiveData<String> get() = _editInput

    private val _dataBase: MutableLiveData<List<Movie>> = MutableLiveData()
    val dataBase: LiveData<List<Movie>> get() = _dataBase

    private var _tabLayoutItem: MutableLiveData<String> = MutableLiveData()
    val tabLayoutItem: LiveData<String> get() = _tabLayoutItem

    private var _url: MutableLiveData<String> = MutableLiveData()
    val url: LiveData<String> get() = _url

    private var _navItem: MutableLiveData<Int> = MutableLiveData()
    val navItem: LiveData<Int> get() = _navItem


    fun getPosition(position: Int) {
        _position.value = position
    }

    fun editInput(text: CharSequence) {
        _editInput.value = text.toString()
    }

    fun getNavBarItem(itemId: Int) {
        _navItem.value = itemId
    }



    fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    fun requestTmdbApi(api: String = url.value!!) {

        viewModelScope.launch(Dispatchers.IO) {
            val clientBuild = OkHttpClient.Builder().build()
            val requestBuild = Request.Builder().url(api).build()


            clientBuild.newCall(requestBuild).enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    _json.postValue(
                        Gson().fromJson(response.body!!.string(), TotalJson::class.java)
                    )
                    Log.d("image", response.toString())

                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("requset", "Failure")
                }
            })
        }
    }

    fun getTabLayoutItem(tab: TabLayout.Tab) {
        _tabLayoutItem.value = tab.text.toString()
    }

    fun editSearchApi(edit: String) {
        _url.value = if (tabLayoutItem.value.equals(TmdbApi.TV, ignoreCase = true))
            "${TmdbApi.TMDB_SEARCH}${TmdbApi.TV}${TmdbApi.TMDB_API}&query=$edit"
        else
            "${TmdbApi.TMDB_SEARCH}${TmdbApi.MOVIE}${TmdbApi.TMDB_API}&query=$edit"
    }

    fun setUrl() {
        _url.value = "${TmdbApi.TMDB_IMAGE}${json.value!!.results[position.value!!].poster_path}"
    }
}
