package com.abdelrahman.movies_list_domain.repository

import com.abdelrahman.domain.models.DataState
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    suspend fun getMovies(): Flow<DataState<MoviesDTO>>
}