package com.abdelrahman.movies_data.remote

import com.abdelrahman.movies_data.models.MoviesResponse
import com.abdelrahman.movies_data.utils.Constants.EndPoints.PLAYING_MOVIES
import com.abdelrahman.movies_data.utils.Constants.QueryParams.PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET(PLAYING_MOVIES)
    suspend fun getMovies(
        @Query(PAGE) page: Int
    ): Response<MoviesResponse>
}