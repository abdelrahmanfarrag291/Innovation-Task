package com.abdelrahman.domain.models

import android.content.Context
import androidx.annotation.StringRes

sealed class StringWrapper {
    data class FromString(val text: String) : StringWrapper()
    data class FromResource(@param:StringRes val resourceId: Int) : StringWrapper()


    fun getStringFromWrapper(context: Context): String {
        return when (this) {
            is FromResource -> context.getString(resourceId)
            is FromString -> text
        }
    }
}