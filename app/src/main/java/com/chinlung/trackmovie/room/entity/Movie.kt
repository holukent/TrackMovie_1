package com.chinlung.trackmovie.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chinlung.trackmovie.MainActivity


@Entity(tableName = "table_movie")
data class Movie(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int = 0,
    @ColumnInfo(name = "movieid") val movieid: String,
    @ColumnInfo(name = "poster_path") val poster_path: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "movieortv") val movieortv: String = "",
) {

}