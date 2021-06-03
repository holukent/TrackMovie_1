package com.chinlung.trackmovie.model

import com.google.gson.annotations.SerializedName

data class TvResult(
    val backdrop_path: String,
    @SerializedName("first_air_date") val release_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    @SerializedName("name") val title: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
)