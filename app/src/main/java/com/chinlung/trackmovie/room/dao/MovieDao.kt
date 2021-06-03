package com.chinlung.trackmovie.room.dao

import androidx.room.*
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.room.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM table_movie ORDER BY _id ASC")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM table_movie WHERE _id IN (:userIds)")
    fun loadAllByIds(userIds:IntArray): List<Movie>

//    @Query("select * from table_movie where title LIKE :name")
//    fun findByName(name:String): Movie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movies:Movie)

    @Delete()
    fun delete(movie:Movie)


}