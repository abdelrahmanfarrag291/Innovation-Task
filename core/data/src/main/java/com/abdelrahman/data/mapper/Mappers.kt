@file:Suppress("UNCHECKED_CAST")

package com.abdelrahman.data.mapper

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorTypes

fun <T, R> Result<T>.mapToDataState(mapDTO: (T) -> R): DataState<R> {
    return when (this) {
        is Result.ResultError -> {
            val error = this.error
            return DataState.DataError(
                errorTypes = ErrorTypes.GeneralError(
                    iconRes = com.abdelrahman.domain.R.drawable.ic_no_internet,
                    error = error!!,
                )
            ) as DataState<R>
        }

        is Result.ResultNoInternetConnection -> DataState.DataError(
            errorTypes = ErrorTypes.NoInternetConnectionError
        )

        is Result.ResultSuccess<T> -> {

            if (data is List<*>) {
                if (data.isEmpty()) {
                    DataState.DataError(
                        errorTypes = ErrorTypes.NoDataError
                    )
                } else {
                    DataState.DataSuccess(
                        result = data.map {
                            mapDTO(it as T)
                        }
                    )
                }
            } else
                DataState.DataSuccess(
                    result = mapDTO(data)
                )
        }
    } as DataState<R>
}