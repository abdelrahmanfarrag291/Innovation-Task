package com.abdelrahman.movies_data.local

import com.abdelrahman.movies_data.local.database.MoviesDao
import com.abdelrahman.movies_data.local.database.MoviesEntity
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) : IMoviesLocalDataSource {
    override suspend fun saveAllMovies(movies: List<MoviesEntity>) {
        moviesDao.insertMoves(movies)
    }

    override suspend fun getAllMovies(): List<MoviesEntity> {
        return moviesDao.getCachedMovies()
    }
}