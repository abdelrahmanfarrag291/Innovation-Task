package com.abdelrahman.movies_data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface MoviesDao {

    @Query("SELECT * from _movies LIMIT :limit OFFSET :offset")
    suspend fun getCachedMovies(limit: Int,offset : Int): List<MoviesEntity>

    @Upsert
    suspend fun insertMoves(movies: List<MoviesEntity>)

    @Query("DELETE FROM _movies")
    suspend fun deleteAll()

}