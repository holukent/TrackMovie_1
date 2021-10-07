package com.chinlung.trackmovie

import com.chinlung.trackmovie.room.roomdatabase.TmdbDataBase


class AppContainer(private val myApplication: MyApplication) {
    val db:TmdbDataBase by lazy {
        TmdbDataBase.getDatabase(myApplication)
    }
}