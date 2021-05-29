package com.chinlung.trackmovie.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chinlung.trackmovie.room.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie_table WHERE mId IN (:userIds)")
    fun loadAllByIds(userIds:IntArray): List<Movie>

    @Query("select * from movie_table where title LIKE :name")
    fun findByName(name:String): Movie

    @Insert()
    fun insertAll(vararg movies:Movie)

    @Delete()
    fun delete(movie:Movie)


}