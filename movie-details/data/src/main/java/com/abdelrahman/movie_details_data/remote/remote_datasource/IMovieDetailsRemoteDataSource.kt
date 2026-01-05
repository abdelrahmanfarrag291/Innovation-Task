package com.abdelrahman.movie_details_data.remote.remote_datasource

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.movie_details_data.remote.models.MovieDetailsResponse

interface IMovieDetailsRemoteDataSource {
    suspend fun getMovieDetails(
        id : Int
    ): Result<MovieDetailsResponse>
}