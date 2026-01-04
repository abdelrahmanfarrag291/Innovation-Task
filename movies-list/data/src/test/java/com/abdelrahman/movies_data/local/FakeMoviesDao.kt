package com.abdelrahman.movies_data.local

import com.abdelrahman.movies_data.local.database.MoviesDao
import com.abdelrahman.movies_data.local.database.MoviesEntity

class FakeMoviesDao : MoviesDao {
    private val mSavedMovies = mutableListOf<MoviesEntity>()
    override suspend fun getCachedMovies(limit: Int,offset : Int): List<MoviesEntity> {
        return mSavedMovies
    }

    override suspend fun insertMoves(movies: List<MoviesEntity>) {
        mSavedMovies.addAll(movies)
    }

    override suspend fun deleteAll() {
        mSavedMovies.clear()
    }
}