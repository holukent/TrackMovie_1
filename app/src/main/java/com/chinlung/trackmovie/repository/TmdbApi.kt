package com.chinlung.trackmovie.repository

import com.chinlung.trackmovie.BuildConfig


class TmdbApi {
    companion object {
        const val MOVIE = "movie"
        const val TV = "tv"
        const val TMDB_API = "?api_key=${BuildConfig.TMDB_KEY}"
        const val TMDB_MOVIE_HOT =
            "https://api.themoviedb.org/3/discover/$MOVIE$TMDB_API&language=zh-TW&sort_by=popularity.desc"
        const val TMDB_TV_HOT =
            "https://api.themoviedb.org/3/discover/$TV$TMDB_API&language=zh-TW&sort_by=popularity.desc"
        const val TMDB_SEARCH_MOVIE =
            "https://api.themoviedb.org/3/search/movie?api_key=${BuildConfig.TMDB_KEY}&language=zh-TW&page=1&include_adult=false&query="
        const val TMDB_GETMOVIE_BY_ID ="https://api.themoviedb.org/3/"
        const val TMDB_SEARCH = "https://api.themoviedb.org/3/search/"
        const val TMDB_IMAGE = "https://image.tmdb.org/t/p/w500"
    }
}