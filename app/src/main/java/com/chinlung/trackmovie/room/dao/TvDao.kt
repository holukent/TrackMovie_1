package com.chinlung.trackmovie.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chinlung.trackmovie.room.entity.Movie
import com.chinlung.trackmovie.room.entity.Tv


@Dao
interface TvDao {
    @Query("SELECT * FROM tv_table")
    fun getAll(): List<Tv>

    @Query("SELECT * FROM tv_table WHERE tId IN (:userIds)")
    fun loadAllById(userIds: IntArray): List<Tv>

    @Query("select * from tv_table where name LIKE :name")
    fun findByName(name: String): Tv

    @Insert()
    fun insertAll(vararg tves: Tv)

    @Delete()
    fun delete(tv: Tv)
}