package com.abdelrahman.movies_data.di

import android.content.Context
import androidx.room.Room
import com.abdelrahman.movies_data.local.IMoviesLocalDataSource
import com.abdelrahman.movies_data.local.MoviesLocalDataSource
import com.abdelrahman.movies_data.local.database.MoviesDao
import com.abdelrahman.movies_data.local.database.MoviesDatabase
import com.abdelrahman.movies_data.remote.MoviesAPI
import com.abdelrahman.movies_data.remote.MoviesRemoteDataSource
import com.abdelrahman.movies_data.remote.MoviesRemoteSourceImpl
import com.abdelrahman.movies_data.repository.MoviesRepository
import com.abdelrahman.movies_data.utils.Constants.Database.MOVIES_DB
import com.abdelrahman.movies_list_domain.repository.IMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesDataModule {

    companion object {
        @Provides
        @Singleton
        fun providesMoviesAPI(
            retrofit: Retrofit
        ): MoviesAPI {
            return retrofit
                .create()
        }

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): MoviesDatabase =
            Room.databaseBuilder(
                context,
                MoviesDatabase::class.java,
                MOVIES_DB
            )
                .fallbackToDestructiveMigration()
                .build()

        @Provides
        fun provideMovieDao(
            database: MoviesDatabase
        ): MoviesDao = database.moviesDao()
    }

    @Binds
    @Singleton
    abstract fun bindsMoviesRepository(moviesRepository: MoviesRepository): IMoviesRepository

    @Binds
    @Singleton
    abstract fun bindsMoviesLocalDataSource(moviesLocalDataSource: MoviesLocalDataSource): IMoviesLocalDataSource


    @Binds
    @Singleton
    abstract fun bindsMoviesRemoteSource(moviesRemoteSourceImpl: MoviesRemoteSourceImpl): MoviesRemoteDataSource
}