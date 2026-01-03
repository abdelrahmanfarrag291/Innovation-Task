package com.abdelrahman.data.utils

import com.google.gson.Gson


inline fun <reified T> String.parseJson(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (_: Exception) {
        null
    }
}