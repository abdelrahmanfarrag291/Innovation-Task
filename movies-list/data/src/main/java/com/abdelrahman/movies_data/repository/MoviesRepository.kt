package com.abdelrahman.movies_data.repository

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorTypes
import com.abdelrahman.domain.models.StringWrapper
import com.abdelrahman.movies_data.local.IMoviesLocalDataSource
import com.abdelrahman.movies_data.mapper.asMovie
import com.abdelrahman.movies_data.mapper.asMovieEntity
import com.abdelrahman.movies_data.models.MoviesResponse
import com.abdelrahman.movies_data.remote.MoviesRemoteDataSource
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import com.abdelrahman.movies_list_domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val iMoviesLocalDataSource: IMoviesLocalDataSource,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : IMoviesRepository {
    override suspend fun getMovies(): Flow<DataState<MoviesDTO>> {
        val moviesDataState = when (val moviesResult = moviesRemoteDataSource.getMovies()) {
            is Result.ResultError -> onMoviesResultError(moviesResult.error)
            is Result.ResultNoInternetConnection -> onMoviesNoInternetConnection()
            is Result.ResultSuccess<MoviesResponse> -> onMoviesSuccess(moviesResult.data)
        }
        return flow { emit(moviesDataState) }
    }

    private suspend fun onMoviesSuccess(data: MoviesResponse): DataState<MoviesDTO> {
        iMoviesLocalDataSource.saveAllMovies(data.movieResponses?.map {
            it.asMovieEntity()
        } as ArrayList)
        return DataState.DataSuccess(
            result = MoviesDTO(
                currentPage = data.page,
                totalPages = data.totalPages,
                moviesList = iMoviesLocalDataSource.getAllMovies().map {
                    it.asMovie()
                }
            )
        )
    }

    private suspend fun onMoviesNoInternetConnection(): DataState<MoviesDTO> {
        val getCachedMovies = iMoviesLocalDataSource.getAllMovies()
        return if (getCachedMovies.isEmpty()) {
            DataState.DataError(errorTypes = ErrorTypes.NoInternetConnectionError)
        } else
            DataState.DataSuccess(
                result = MoviesDTO(
                    totalPages = 1,
                    currentPage = 1,
                    moviesList = getCachedMovies.map {
                        return@map it.asMovie()
                    }
                ))
    }

    private fun onMoviesResultError(error: StringWrapper?): DataState<MoviesDTO> {
        return DataState.DataError(
            errorTypes = ErrorTypes.GeneralError(
                error = error!!,
                iconRes = com.abdelrahman.domain.R.drawable.ic_no_internet
            )
        )
    }


}