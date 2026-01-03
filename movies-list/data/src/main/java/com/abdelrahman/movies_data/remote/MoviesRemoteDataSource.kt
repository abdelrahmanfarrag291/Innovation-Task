package com.abdelrahman.movies_data.remote

import com.abdelrahman.data.remote_datasource.result.Result

interface MoviesRemoteDataSource {
    suspend fun getMovies(): Result<String>
}