package com.abdelrahman.common_data.di

import com.abdelrahman.common_data.interceptor.MoviesInterceptor
import com.abdelrahman.common_data.remote.ErrorParsing
import com.abdelrahman.data.remote_datasource.error.IErrorModel
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonMoviesDataModule {
    companion object {
        @Provides
        @Singleton
        fun providesMoviesAPI(
            client: OkHttpClient,
            gson: Gson
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(com.abdelrahman.common_data.BuildConfig.TMDB_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }
    }

    @Binds
    @Singleton
    abstract fun bindsErrorModel(moviesErrorModel: ErrorParsing): IErrorModel

    @Binds
    @Singleton
    abstract fun bindsInterceptor(moviesInterceptor: MoviesInterceptor): Interceptor

}