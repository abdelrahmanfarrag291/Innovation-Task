package com.abdelrahman.domain.models

import androidx.annotation.DrawableRes
import com.abdelrahman.domain.R

sealed class ErrorTypes(val errorMessage: StringWrapper,
    @param:DrawableRes val icon: Int
) {
    data class GeneralError(
        val error: StringWrapper,
        val iconRes: Int
    ) : ErrorTypes(
        error, iconRes
    )

    data object NoInternetConnectionError : ErrorTypes(
        StringWrapper.FromResource(
            R.string.no_internet_connection
        ), R.drawable.ic_no_internet
    )

    data object NoDataError : ErrorTypes(
        StringWrapper.FromResource(
            R.string.no_data,

            ),
        R.drawable.ic_no_internet
        /**TODO REMOVE THIS WITH RIGHT ICON AS I HAD SOME CONNECTIVITY ISSUES**/
    )
}