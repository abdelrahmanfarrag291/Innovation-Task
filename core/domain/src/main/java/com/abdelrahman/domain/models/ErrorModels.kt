package com.abdelrahman.domain.models

import androidx.annotation.DrawableRes
import com.abdelrahman.domain.R

sealed class ErrorModels(val errorMessage: StringWrapper,
                         @param:DrawableRes val icon: Int
) {
    data class GeneralError(
        val error: StringWrapper,
        val iconRes: Int
    ) : ErrorModels(
        error, iconRes
    )

    data object NoInternetConnectionError : ErrorModels(
        StringWrapper.FromResource(
            R.string.no_internet_connection
        ), R.drawable.ic_no_internet
    )

    data object NoDataError : ErrorModels(
        StringWrapper.FromResource(
            R.string.no_data,

            ),
        R.drawable.ic_no_data
    )
}