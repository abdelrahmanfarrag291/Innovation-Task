package com.abdelrahman.movie_details_data.remote.remote_datasource

import com.abdelrahman.data.remote_datasource.BaseRemoteDataSource
import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.movie_details_data.remote.api.MovieDetailsAPI
import com.abdelrahman.movie_details_data.remote.models.MovieDetailsResponse
import javax.inject.Inject

class MovieDetailsRemoteDataSource @Inject constructor(
    private val iValidateRemoteSource: IValidateRemoteSource,
    private val iCheckNetworkState: ICheckNetworkState,
    private val movieDetailsAPI: MovieDetailsAPI
) : BaseRemoteDataSource(iValidateRemoteSource, iCheckNetworkState),
    IMovieDetailsRemoteDataSource {

    override suspend fun getMovieDetails(id: Int): Result<MovieDetailsResponse> {
        return safeAPICALL {
            movieDetailsAPI.getMoviesDetails(id)
        }
    }

}