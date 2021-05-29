package com.chinlung.trackmovie.model

data class MovieJson(
    val page: Int,
    val results: List<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)