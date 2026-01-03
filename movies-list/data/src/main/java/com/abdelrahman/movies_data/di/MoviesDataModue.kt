package com.abdelrahman.movies_data.di

import com.abdelrahman.data.remote_datasource.error.IErrorModel
import com.abdelrahman.data.remote_datasource.interceptor.INetworkInterceptor
import com.abdelrahman.movies_data.interceptor.MoviesInterceptor
import com.abdelrahman.movies_data.models.MoviesErrorModel
import com.abdelrahman.movies_data.remote.ErrorParsing
import com.abdelrahman.movies_data.remote.MoviesAPI
import com.abdelrahman.movies_data.remote.MoviesRemoteDataSource
import com.abdelrahman.movies_data.remote.MoviesRemoteSourceImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesDataModule {

    companion object {
        @Provides
        @Singleton
        fun providesMoviesAPI(
            client: OkHttpClient,
            gson: Gson
        ): MoviesAPI {
            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create()
        }
    }

    @Binds
    @Singleton
    abstract fun bindsInterceptor(moviesInterceptor: MoviesInterceptor): INetworkInterceptor

    @Binds
    @Singleton
    abstract fun bindsErrorModel(moviesErrorModel: ErrorParsing): IErrorModel

    @Binds
    @Singleton
    abstract fun bindsMoviesRemoteSource(moviesRemoteSourceImpl: MoviesRemoteSourceImpl): MoviesRemoteDataSource
}