package com.abdelrahman.movies_data.remote

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.movies_data.models.MoviesResponse

interface MoviesRemoteDataSource {
    suspend fun getMovies(): Result<MoviesResponse>
}