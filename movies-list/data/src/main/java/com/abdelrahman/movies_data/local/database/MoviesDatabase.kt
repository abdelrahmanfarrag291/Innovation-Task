package com.abdelrahman.movies_data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MoviesEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
}