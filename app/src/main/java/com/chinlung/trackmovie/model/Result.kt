package com.chinlung.trackmovie.model

import com.google.gson.annotations.SerializedName

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val origin_country: List<String>,
    val original_name: String,
    @SerializedName("name", alternate = ["title"])
    val title: String,
    @SerializedName("first_air_date", alternate = ["release_date"])
    val release_date: String,
)