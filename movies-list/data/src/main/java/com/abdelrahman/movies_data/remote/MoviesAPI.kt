package com.abdelrahman.movies_data.remote

import retrofit2.Response
import retrofit2.http.GET

interface MoviesAPI {

    @GET("movie/now_playing")
    suspend fun getMovies(): Response<String>
}