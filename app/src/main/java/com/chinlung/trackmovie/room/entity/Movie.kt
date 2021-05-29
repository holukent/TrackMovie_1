package com.chinlung.trackmovie.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true) val mId:Int,
    @ColumnInfo(name = "title") val titleName: String,
) {

}