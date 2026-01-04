package com.abdelrahman.common_data.remote.models

import com.google.gson.annotations.SerializedName

data class MoviesErrorModel(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null
)