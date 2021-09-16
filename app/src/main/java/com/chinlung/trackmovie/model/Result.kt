package com.chinlung.trackmovie.model

import android.view.View
import com.google.gson.annotations.SerializedName

data class Result(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.1,
    val poster_path: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.1,
    val vote_count: Int = 0,
    val origin_country: List<String> = emptyList(),
    val original_name: String = "",
    @SerializedName("name", alternate = ["title"])
    val title: String ="",
    @SerializedName("first_air_date", alternate = ["release_date"])
    val release_date: String = "",
    var expand:Int = View.GONE
)