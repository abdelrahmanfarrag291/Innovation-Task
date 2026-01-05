package com.abdelrahman.data.helper

import com.abdelrahman.data.remote_datasource.error.IErrorModel
import com.abdelrahman.data.remote_datasource.models.ApiErrorModel
import com.abdelrahman.data.utils.parseJson

class FakeErrorModel : IErrorModel {
    override fun parseErrorModel(errorBody: String?): ApiErrorModel {
        val error = errorBody?.parseJson<SampleAPIError>()
        return ApiErrorModel(
            code = error?.code,
            message = error?.message
        )
    }
}