package com.chinlung.trackmovie.room.dao

import androidx.room.*
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.room.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM table_movie ORDER BY _id DESC")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM table_movie WHERE movieid IN (:userIds)")
    fun loadAllByIds(userIds:IntArray): List<Movie>

    @Query("select * from table_movie where poster_path LIKE :poster_path")
    fun findByName(poster_path:String): Movie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movies:Movie)

    @Query("DELETE FROM table_movie WHERE _id = :id")
    fun delete(id:Int)


}