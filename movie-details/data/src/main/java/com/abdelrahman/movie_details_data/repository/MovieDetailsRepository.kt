package com.abdelrahman.movie_details_data.repository

import com.abdelrahman.data.mapper.mapToDataState
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.movie_details_data.mappers.asMovieDetailsDto
import com.abdelrahman.movie_details_data.remote.remote_datasource.IMovieDetailsRemoteDataSource
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO
import com.abdelrahman.movie_details_domain.repository.IGetMovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val iMovieDetailsRemoteDataSource: IMovieDetailsRemoteDataSource
) : IGetMovieDetailsRepository {

    override suspend fun getMovieDetails(id: Int): Flow<DataState<MovieDetailsDTO>> {
        return iMovieDetailsRemoteDataSource.getMovieDetails(id)
            .mapToDataState { movieDetailsResponse ->
                movieDetailsResponse.asMovieDetailsDto()
            }
    }
}