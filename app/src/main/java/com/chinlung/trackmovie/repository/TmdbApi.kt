package com.chinlung.trackmovie.repository

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TmdbApi(private val context: Context) {

    private fun loadTmdbMovie(): String {
        return "https://api.themoviedb.org/3/discover/tv?api_key=3295120c2c04434d28ac1d5ff19a3af0&sort_by=popularity.desc"

    }

    private fun loadTmdbTv(): String {
        return "https://api.themoviedb.org/3/discover/movie?api_key=3295120c2c04434d28ac1d5ff19a3af0&sort_by=popularity.desc"

    }

//    fun requestApi(url: String, movieortv:String){
//        val queue = Volley.newRequestQueue(context)
//        val stringRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            {
//            if (movieortv == "movie") {
//
//            }
//            },
//            {}
//        )
//        queue.add(stringRequest)
//    }
}