package com.abdelrahman.movies_data.interceptor

import com.abdelrahman.data.remote_datasource.interceptor.INetworkInterceptor
import com.abdelrahman.movies_data.remote.ApiKey
import com.abdelrahman.movies_data.utils.Constants.Network.ACCEPT_LANGUAGE
import com.abdelrahman.movies_data.utils.Constants.Network.AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MoviesInterceptor @Inject constructor() : INetworkInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalURl = originalRequest.url
        val newUrl = originalURl.newBuilder()
            .addQueryParameter(ACCEPT_LANGUAGE, "en")
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader(AUTHORIZATION,"Bearer ${ApiKey.getApiKey()}")
            .build()
        return chain.proceed(newRequest)
    }
}