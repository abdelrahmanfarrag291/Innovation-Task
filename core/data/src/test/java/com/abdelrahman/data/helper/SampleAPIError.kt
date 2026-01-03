package com.abdelrahman.data.helper

import com.google.gson.annotations.SerializedName

data class SampleAPIError(
    @SerializedName("code")
    val code: Int?=null,
    @SerializedName("message")
    val message: String?=null,
    @SerializedName("success")
    val isSuccess: Boolean?=null
)
