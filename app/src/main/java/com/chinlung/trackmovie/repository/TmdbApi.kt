package com.chinlung.trackmovie.repository

import com.chinlung.trackmovie.BuildConfig


class TmdbApi {
    companion object {
        const val MOVIE = "movie"
        const val TV = "tv"
        const val TMDB_API = "?api_key=${BuildConfig.TMDB_KEY}"
        const val TMDB_MOVIE_HOT =
            "https://api.themoviedb.org/3/discover/$MOVIE$TMDB_API&sort_by=popularity.desc"
        const val TMDB_TV_HOT =
            "https://api.themoviedb.org/3/discover/$TV$TMDB_API&sort_by=popularity.desc"
        const val TMDB_SEARCH_MOVIE =
            "https://api.themoviedb.org/3/search/movie?api_key=${BuildConfig.TMDB_KEY}&language=en-US&page=1&include_adult=false&query="
        const val TMDB_GETMOVIE_BY_ID ="https://api.themoviedb.org/3/movie/"

        const val TMDB_SEARCH = "https://api.themoviedb.org/3/search/"
    }
    fun loadTmdbSearch(movieortv: String): String {

        return "$TMDB_SEARCH$movieortv$TMDB_API"
    }

    fun getByid(id:Int, movieOrtv:String): String {
        return "https://api.themoviedb.org/3/$movieOrtv/$id?api_key=${BuildConfig.TMDB_KEY}"

    }
}