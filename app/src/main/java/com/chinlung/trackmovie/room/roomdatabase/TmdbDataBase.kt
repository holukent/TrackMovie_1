package com.chinlung.trackmovie.room.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chinlung.trackmovie.MainActivity
import com.chinlung.trackmovie.room.dao.MovieDao
import com.chinlung.trackmovie.room.dao.TvDao
import com.chinlung.trackmovie.room.entity.Movie
import com.chinlung.trackmovie.room.entity.Tv

@Database(entities = [Tv::class, Movie::class], version = 1)
abstract class TmdbDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvDao(): TvDao

    companion object{
        @Volatile
        private var INSTANCE: TmdbDataBase? = null

        fun getDatabase(context: Context): TmdbDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TmdbDataBase::class.java,
                    MainActivity.DATABASE_TMDB
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}