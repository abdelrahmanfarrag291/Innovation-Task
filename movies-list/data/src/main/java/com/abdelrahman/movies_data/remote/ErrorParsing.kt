package com.abdelrahman.movies_data.remote

import com.abdelrahman.data.remote_datasource.error.IErrorModel
import com.abdelrahman.data.remote_datasource.models.ApiErrorModel
import com.abdelrahman.data.utils.parseJson
import com.abdelrahman.movies_data.models.MoviesErrorModel
import javax.inject.Inject

class ErrorParsing @Inject constructor() : IErrorModel {
    override fun parseErrorModel(errorBody: String?): ApiErrorModel {
        val error = errorBody?.parseJson<MoviesErrorModel>()
        return ApiErrorModel(
            code = error?.code,
            message = error?.statusMessage
        )
    }
}