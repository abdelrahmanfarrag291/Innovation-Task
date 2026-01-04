package com.abdelrahman.movies_data.remote

import com.abdelrahman.movies_data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/now_playing")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Response<MoviesResponse>
}