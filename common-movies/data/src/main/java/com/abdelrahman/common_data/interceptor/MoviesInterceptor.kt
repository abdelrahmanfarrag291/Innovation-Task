package com.abdelrahman.common_data.interceptor

import com.abdelrahman.common_data.remote.ApiKey
import com.abdelrahman.common_data.utils.Constants.LANGUAGE_ENGLISH
import com.abdelrahman.common_data.utils.Constants.Network.ACCEPT_LANGUAGE
import com.abdelrahman.common_data.utils.Constants.Network.AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MoviesInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalURl = originalRequest.url
        val newUrl = originalURl.newBuilder()
            .addQueryParameter(ACCEPT_LANGUAGE, LANGUAGE_ENGLISH)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader(AUTHORIZATION,"Bearer ${ApiKey.getApiKey()}")
            .build()
        return chain.proceed(newRequest)
    }
}