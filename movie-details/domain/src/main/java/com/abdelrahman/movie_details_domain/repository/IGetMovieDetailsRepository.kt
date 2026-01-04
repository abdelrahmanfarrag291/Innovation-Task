package com.abdelrahman.movie_details_domain.repository

import com.abdelrahman.domain.models.DataState
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO
import kotlinx.coroutines.flow.Flow

interface IGetMovieDetailsRepository {
    suspend fun getMovieDetails(
        id: Int
    ): Flow<DataState<MovieDetailsDTO>>
}