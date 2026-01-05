package com.abdelrahman.data.remote_datasource.error

import com.abdelrahman.data.remote_datasource.models.ApiErrorModel

interface IErrorModel {
    fun parseErrorModel(errorBody: String?): ApiErrorModel
}