package com.abdelrahman.movies_data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * from _movies")
    suspend fun getCachedMovies(): List<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoves(movies: List<MoviesEntity>)
}