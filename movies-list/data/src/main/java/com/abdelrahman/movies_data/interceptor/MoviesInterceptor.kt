package com.abdelrahman.movies_data.interceptor

import com.abdelrahman.data.remote_datasource.interceptor.INetworkInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MoviesInterceptor @Inject constructor() : INetworkInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalURl = originalRequest.url
        val newUrl = originalURl.newBuilder()
            .addQueryParameter("key", "mfmf")
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}