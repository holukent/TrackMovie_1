package com.chinlung.trackmovie.data

data class TvJson(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)