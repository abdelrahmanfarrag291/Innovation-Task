package com.abdelrahman.data.remote_datasource.validate_remote

import com.abdelrahman.data.remote_datasource.result.Result
import retrofit2.Response

interface IValidateRemoteSource {
    suspend fun <T> validate(response: Response<T>): Result<T>
}