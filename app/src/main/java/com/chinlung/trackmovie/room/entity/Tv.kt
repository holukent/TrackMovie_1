package com.chinlung.trackmovie.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tv_table")
data class Tv(
    @PrimaryKey(autoGenerate = true) val tId:Int,
    @ColumnInfo(name = "name") val titleName:String
) {
}