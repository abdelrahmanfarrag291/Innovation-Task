package com.abdelrahman.movies_data.remote

import com.abdelrahman.data.remote_datasource.BaseRemoteDataSource
import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.movies_data.models.MoviesResponse
import javax.inject.Inject

class MoviesRemoteSourceImpl @Inject constructor(
    private val iValidateRemoteSource: IValidateRemoteSource,
    private val iCheckNetworkState: ICheckNetworkState,
    private val iMoviesAPI: MoviesAPI
) : BaseRemoteDataSource(iValidateRemoteSource,iCheckNetworkState), MoviesRemoteDataSource {

    override suspend fun getMovies(): Result<MoviesResponse> {
        return safeAPICALL {
            iMoviesAPI.getMovies()
        }
    }
}