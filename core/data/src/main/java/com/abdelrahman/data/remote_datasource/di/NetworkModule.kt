package com.abdelrahman.data.remote_datasource.di

import com.abdelrahman.data.remote_datasource.interceptor.INetworkInterceptor
import com.abdelrahman.data.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        iNetworkInterceptor: INetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.OkHTTPConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.OkHTTPConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.OkHTTPConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(iNetworkInterceptor)
            .build()
    }

}