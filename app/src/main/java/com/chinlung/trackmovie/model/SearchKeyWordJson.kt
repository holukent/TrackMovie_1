package com.chinlung.trackmovie.model

data class SearchKeyWordJson(
    val page: Int,
    val results: List<SearchResult>,
    val total_pages: Int,
    val total_results: Int
)