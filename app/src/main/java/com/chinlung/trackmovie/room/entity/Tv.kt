package com.chinlung.trackmovie.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chinlung.trackmovie.MainActivity


@Entity(tableName = MainActivity.TABLE_TV)
data class Tv(
    @PrimaryKey(autoGenerate = true) val tId:Int,
    @ColumnInfo(name = "name") val titleName:String
) {
}