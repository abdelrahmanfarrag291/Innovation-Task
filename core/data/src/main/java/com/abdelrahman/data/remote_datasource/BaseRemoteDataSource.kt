package com.abdelrahman.data.remote_datasource

import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.StringWrapper
import retrofit2.Response
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
open class BaseRemoteDataSource @Inject constructor(
    private val iValidateRemoteSource: IValidateRemoteSource,
    private val iCheckNetworkState: ICheckNetworkState
) {

    suspend fun <T> safeAPICALL(apiCall: () -> Response<T>): Result<T> {
        return if (iCheckNetworkState.isConnected())
            iValidateRemoteSource.validate(apiCall())
        else
            Result.ResultNoInternetConnection(
                error = StringWrapper.FromResource(R.string.no_internet_connection)
            ) as Result<T>
    }
}