package com.abdelrahman.movies_data.repository

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorModels
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
    override suspend fun getMovies(page: Int): Flow<DataState<MoviesDTO>> {

        val moviesDataState = when (val moviesResult = moviesRemoteDataSource.getMovies(page)) {
            is Result.ResultError -> onMoviesResultError(moviesResult.error)
            is Result.ResultNoInternetConnection -> onMoviesNoInternetConnection(page)
            is Result.ResultSuccess<MoviesResponse> -> onMoviesSuccess(moviesResult.data)
        }
        return flow { emit(moviesDataState) }
    }

    private suspend fun onMoviesSuccess(data: MoviesResponse): DataState<MoviesDTO> {
        val page = data.page ?: 1
        if (page == 1) iMoviesLocalDataSource.clearAllMovies()
        val moviesEntities = data.movieResponses
            ?.map { it.asMovieEntity() }
            ?.toList() ?: emptyList()
        iMoviesLocalDataSource.saveAllMovies(moviesEntities)
        val moviesFromDb = iMoviesLocalDataSource.getAllMovies(page - 1)
            .map { it.asMovie() }
            .toCollection(ArrayList())

        return DataState.DataSuccess(
            result = MoviesDTO(
                currentPage = page,
                totalPages = data.totalPages,
                moviesList = moviesFromDb
            )
        )
    }

    private suspend fun onMoviesNoInternetConnection(page: Int): DataState<MoviesDTO> {
        val getCachedMovies =
            iMoviesLocalDataSource.getAllMovies(page - 1).map { it.asMovie() }
                .toCollection(ArrayList())
        val totalPages = (getCachedMovies.size + 10) / 10
        return if (getCachedMovies.isEmpty()) {
            DataState.DataError(errorModels = ErrorModels.NoInternetConnectionError)
        } else {
            DataState.DataSuccess(
                result = MoviesDTO(
                    totalPages = totalPages,
                    currentPage = page,
                    moviesList = getCachedMovies
                )
            )
        }
    }

    private fun onMoviesResultError(error: StringWrapper?): DataState<MoviesDTO> {
        return DataState.DataError(
            errorModels = ErrorModels.GeneralError(
                error = error!!,
                iconRes = com.abdelrahman.domain.R.drawable.ic_no_internet
            )
        )
    }


}