@file:Suppress("UNCHECKED_CAST")

package com.abdelrahman.data.remote_datasource.validate_remote

import com.abdelrahman.data.remote_datasource.error.IErrorModel
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.utils.Constants.ErrorCodes.GENERAL_ERROR_CODE
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.StringWrapper
import retrofit2.Response
import javax.inject.Inject

class ValidateRemoteSource @Inject constructor(
    private val iErrorModel: IErrorModel
) : IValidateRemoteSource {

    override suspend fun <T> validate(response: Response<T>): Result<T> {
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.ResultSuccess(data = body)
                } else
                    Result.ResultError(
                        errorCode = GENERAL_ERROR_CODE,
                        error = StringWrapper.FromResource(R.string.something_went_wrong)
                    )
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val error = iErrorModel.parseErrorModel(errorBody.string())
                    Result.ResultError(
                        errorCode = error.code,
                        error = if (error.message.isNullOrEmpty())
                            StringWrapper.FromResource(R.string.something_went_wrong)
                        else
                            StringWrapper.FromString(error.message)
                    )
                } else {
                    Result.ResultError(
                        errorCode = GENERAL_ERROR_CODE,
                        error = StringWrapper.FromResource(R.string.something_went_wrong)
                    )
                }
            }
        } catch (_: Exception) {
            Result.ResultError(
                errorCode = GENERAL_ERROR_CODE,
                error = StringWrapper.FromResource(R.string.something_went_wrong)
            )
        } as Result<T>
    }
}