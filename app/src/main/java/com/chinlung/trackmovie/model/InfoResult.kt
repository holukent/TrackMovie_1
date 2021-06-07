package com.chinlung.trackmovie.model

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class InfoResult(
    @SerializedName("name", alternate = ["title"]) val title: String,
    val overview: String,
    val genres: JSONArray,
    val poster: String,
    @SerializedName("first_air_date", alternate = ["release_date"])
    val release_date: String
) {
}