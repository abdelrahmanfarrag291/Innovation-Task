package com.abdelrahman.movies_data.local

import com.abdelrahman.data.local_datasource.IBaseLocalDataSource
import com.abdelrahman.movies_data.local.database.MoviesEntity

interface IMoviesLocalDataSource : IBaseLocalDataSource {
    suspend fun saveAllMovies(movies: List<MoviesEntity>)
    suspend fun getAllMovies(): List<MoviesEntity>
}