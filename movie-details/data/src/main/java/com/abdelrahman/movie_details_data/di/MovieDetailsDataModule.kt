package com.abdelrahman.movie_details_data.di

import com.abdelrahman.movie_details_data.remote.api.MovieDetailsAPI
import com.abdelrahman.movie_details_data.remote.remote_datasource.IMovieDetailsRemoteDataSource
import com.abdelrahman.movie_details_data.remote.remote_datasource.MovieDetailsRemoteDataSource
import com.abdelrahman.movie_details_data.repository.MovieDetailsRepository
import com.abdelrahman.movie_details_domain.repository.IGetMovieDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailsDataModule {

    companion object {
        @Provides
        @Singleton
        fun providesMovieDetailsApi(retrofit: Retrofit): MovieDetailsAPI {
            return retrofit.create()
        }
    }

    @Binds
    @Singleton
    abstract fun bindsMovieDetailsRemoteDataSource(movieDetailsRemoteDataSource: MovieDetailsRemoteDataSource): IMovieDetailsRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsMovieDetailsRepository(movieDetailsRepository: MovieDetailsRepository): IGetMovieDetailsRepository
}