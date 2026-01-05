package com.abdelrahman.movies_data.repository

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.domain.models.StringWrapper
import com.abdelrahman.movies_data.local.FakeMoviesDao
import com.abdelrahman.movies_data.local.MoviesLocalDataSource
import com.abdelrahman.movies_data.local.database.MoviesEntity
import com.abdelrahman.movies_data.mapper.asMovie
import com.abdelrahman.movies_data.mapper.asMovieEntity
import com.abdelrahman.movies_data.models.MovieResponse
import com.abdelrahman.movies_data.models.MoviesResponse
import com.abdelrahman.movies_data.remote.MoviesRemoteDataSource
import com.abdelrahman.movies_list_domain.entity.Movie
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import com.abdelrahman.movies_list_domain.repository.IMoviesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class MoviesRepositoryShould {

    //SUT
    private lateinit var mIMoviesRepository: IMoviesRepository
    private val moviesRemoteDataSource = mock<MoviesRemoteDataSource>()
    private val fakeDao = FakeMoviesDao()
    private val moviesLocalDataSource = MoviesLocalDataSource(fakeDao)


    @Before
    fun init() {
        mIMoviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)
    }

    @Test
    fun `call movies returns error , then it should return data state of error`() = runTest {
        val moviesResult = Result.ResultError(
            errorCode = null,
            error = StringWrapper.FromString("error")
        )
        whenever(moviesRemoteDataSource.getMovies(1)).thenReturn(moviesResult)
        val expected = DataState.DataError(
            errorModels = ErrorModels.GeneralError(
                iconRes = R.drawable.ic_error,
                error = moviesResult.error!!
            )
        )
        val actual = mIMoviesRepository.getMovies(1).first()
        assertEquals(expected, actual)
    }

    @Test
    fun `call movies returns no internet connection while there were save data , then should return data state success with saved movies`() =
        runTest {
            val movieEntity = MoviesEntity(id = 1, movieId = 1)
            val moviesResult =
                Result.ResultNoInternetConnection(error = StringWrapper.FromResource(R.string.no_internet_connection)) as Result<MoviesResponse>
            moviesLocalDataSource.saveAllMovies(listOf(movieEntity))
            whenever(moviesRemoteDataSource.getMovies(1)).thenReturn(moviesResult)
            val expected = DataState.DataSuccess(
                result = MoviesDTO(
                    totalPages = 1,
                    currentPage = 1,
                    moviesList = arrayListOf(
                        movieEntity.asMovie()
                    )
                )
            )
            val actual = mIMoviesRepository.getMovies(1).first()
            assertEquals(expected, actual)
        }

    @Test
    fun `call movies return no internet connection and there is no saved data , then should return data state that have no internet connect`() =
        runTest {
            val moviesResult =
                Result.ResultNoInternetConnection(error = StringWrapper.FromResource(R.string.no_internet_connection)) as Result<MoviesResponse>
            whenever(moviesRemoteDataSource.getMovies(1)).thenReturn(moviesResult)
            val expected = DataState.DataError(errorModels = ErrorModels.NoInternetConnectionError)
            val actual = mIMoviesRepository.getMovies(1).first()
            assertEquals(expected, actual)
        }

    @Test
    fun `call movies end points returns success then save movies to database and return movies from local datasource`() =
        runTest {
            val moviesResponse =MovieResponse(originalTitle = "name1")
            val moviesResult = Result.ResultSuccess(
                data = MoviesResponse(
                    page = 1,
                    totalPages = 1,
                    movieResponses = listOf(moviesResponse)
                )
            )
            whenever(moviesRemoteDataSource.getMovies(1)).thenReturn(moviesResult)
            val expected = DataState.DataSuccess(
                result = MoviesDTO(
                    totalPages = 1,
                    currentPage = 1,
                    moviesList = arrayListOf(
                        moviesResponse.asMovieEntity().asMovie()
                    )
                )
            )


            val actual = mIMoviesRepository.getMovies(1).first()
            assertEquals(expected, actual)
        }
}