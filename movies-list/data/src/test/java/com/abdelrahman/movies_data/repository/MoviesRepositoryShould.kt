package com.abdelrahman.movies_data.repository

import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorTypes
import com.abdelrahman.domain.models.StringWrapper
import com.abdelrahman.movies_data.local.FakeMoviesDao
import com.abdelrahman.movies_data.local.MoviesLocalDataSource
import com.abdelrahman.movies_data.local.database.MoviesEntity
import com.abdelrahman.movies_data.mapper.asMovieEntity
import com.abdelrahman.movies_data.models.MovieResponse
import com.abdelrahman.movies_data.models.MoviesResponse
import com.abdelrahman.movies_data.remote.MoviesRemoteDataSource
import com.abdelrahman.movies_list_domain.entity.Movie
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import com.abdelrahman.movies_list_domain.repository.IMoviesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


/**
 * MoviesRepository is to decide where to get the data from either [com.abdelrahman.movies_data.local.MoviesLocalDataSource] nor [com.abdelrahman.movies_data.remote.MoviesRemoteDataSource]
 *
 *  ## Test cases
 *  1-when Result is [com.abdelrahman.data.remote_datasource.result.Result.ResultSuccess] then it should save data to [com.abdelrahman.movies_data.local.database.MoviesDatabase] AND then return results from the [com.abdelrahman.movies_data.local.database.MoviesDatabase]
 */
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
        whenever(moviesRemoteDataSource.getMovies()).thenReturn(moviesResult)
        val expected = DataState.DataError(
            errorTypes = ErrorTypes.GeneralError(
                iconRes = R.drawable.ic_no_internet,
                error = moviesResult.error!!
            )
        )
        val actual = mIMoviesRepository.getMovies().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `call movies returns no internet connection while there were save data , then should return data state success with saved movies`() =
        runTest {
            val moviesResult =
                Result.ResultNoInternetConnection(error = StringWrapper.FromResource(R.string.no_internet_connection)) as Result<MoviesResponse>
            moviesLocalDataSource.saveAllMovies(listOf(MoviesEntity(id = 1, movieId = 1)))
            whenever(moviesRemoteDataSource.getMovies()).thenReturn(moviesResult)
            val expected = DataState.DataSuccess(
                result = MoviesDTO(
                    totalPages = 1,
                    currentPage = 1,
                    moviesList = listOf(
                        Movie(
                            movieId = 1
                        )
                    )
                )
            )
            val actual = mIMoviesRepository.getMovies().first()
            assertEquals(expected, actual)
        }

    @Test
    fun `call movies return no internet connection and there is no saved data , then should return data state that have no internet connect`() =
        runTest {
            val moviesResult =
                Result.ResultNoInternetConnection(error = StringWrapper.FromResource(R.string.no_internet_connection)) as Result<MoviesResponse>
            whenever(moviesRemoteDataSource.getMovies()).thenReturn(moviesResult)
            val expected = DataState.DataError(errorTypes = ErrorTypes.NoInternetConnectionError)
            val actual = mIMoviesRepository.getMovies().first()
            assertEquals(expected, actual)
        }

    @Test
    fun `call movies end points returns success then save movies to database and return movies from local datasource`() =
        runTest {
            val moviesResult = Result.ResultSuccess(
                data = MoviesResponse(
                    page = 1,
                    totalPages = 1,
                    movieResponses = listOf(MovieResponse(originalTitle = "name1"))
                )
            )
            whenever(moviesRemoteDataSource.getMovies()).thenReturn(moviesResult)
            val expected = DataState.DataSuccess(
                result = MoviesDTO(
                    totalPages = 1,
                    currentPage = 1,
                    moviesList = listOf(
                        Movie(
                            movieName = "name1"
                        )
                    )
                )
            )


            val actual = mIMoviesRepository.getMovies().first()
            assertEquals(expected, actual)
        }
}