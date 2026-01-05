package com.abdelrahman.movie_details_data.remote.api

import com.abdelrahman.movie_details_data.remote.models.MovieDetailsResponse
import com.abdelrahman.movie_details_data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsAPI {
    @GET(Constants.EndPoints.MOVIE_DETAILS)
    suspend fun getMoviesDetails(
        @Path(Constants.Paths.ID_PATH) id: Int
    ): Response<MovieDetailsResponse>
}