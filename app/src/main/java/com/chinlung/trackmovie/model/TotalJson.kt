package com.chinlung.trackmovie.model

import android.os.Parcel
import android.os.Parcelable

data class TotalJson(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)