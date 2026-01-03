package com.abdelrahman.data.remote_datasource.result

import com.abdelrahman.domain.models.StringWrapper

sealed interface Result<T> {
    data class ResultSuccess<T>(val data: T) : Result<T>
    data class ResultError(val errorCode: Int?, val error: StringWrapper?) : Result<Nothing>
    data class ResultNoInternetConnection(val error: StringWrapper?) : Result<Nothing>
}