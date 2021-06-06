package com.chinlung.trackmovie.room.dao

import androidx.room.*
import com.chinlung.trackmovie.room.entity.Movie
import com.chinlung.trackmovie.room.entity.Tv


@Dao
interface TvDao {
    @Query("SELECT * FROM tv_table ORDER BY tvid ASC")
    fun getAll(): List<Tv>

    @Query("SELECT * FROM tv_table WHERE tvid IN (:userIds)")
    fun loadAllById(userIds: IntArray): List<Tv>


    @Insert()
    fun insertAll(vararg tves: Tv)

    @Delete()
    fun delete(tv: Tv)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tv:Tv)
}