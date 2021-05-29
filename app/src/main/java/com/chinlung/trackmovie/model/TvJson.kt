package com.chinlung.trackmovie.model

data class TvJson(
    val page: Int,
    val results: List<TvResult>,
    val total_pages: Int,
    val total_results: Int
)