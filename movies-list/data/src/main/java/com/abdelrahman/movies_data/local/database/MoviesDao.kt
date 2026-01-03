package com.abdelrahman.movies_data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface MoviesDao {

    @Query("SELECT * from _movies")
    suspend fun getCachedMovies(): List<MoviesEntity>

    @Upsert
    suspend fun insertMoves(movies: List<MoviesEntity>)
}