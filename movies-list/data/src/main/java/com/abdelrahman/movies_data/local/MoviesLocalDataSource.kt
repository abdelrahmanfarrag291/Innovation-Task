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

    override suspend fun getAllMovies(page: Int): List<MoviesEntity> {
        val offset =page * 20
        return moviesDao.getCachedMovies(20, offset)
    }

    override suspend fun clearAllMovies() {
        moviesDao.deleteAll()
    }
}