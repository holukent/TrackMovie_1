package com.chinlung.trackmovie.model

import android.os.Parcel
import android.os.Parcelable

data class MovieJson1(
    val page: Int,
    val results: List<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)
