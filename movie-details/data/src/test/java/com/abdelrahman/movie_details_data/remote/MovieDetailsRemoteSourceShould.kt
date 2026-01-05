package com.abdelrahman.movie_details_data.remote

import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.StringWrapper
import com.abdelrahman.movie_details_data.remote.api.MovieDetailsAPI
import com.abdelrahman.movie_details_data.remote.models.MovieDetailsResponse
import com.abdelrahman.movie_details_data.remote.remote_datasource.IMovieDetailsRemoteDataSource
import com.abdelrahman.movie_details_data.remote.remote_datasource.MovieDetailsRemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MovieDetailsRemoteSourceShould {

    //SUT
    private lateinit var movieDetailsRemoteDataSource: IMovieDetailsRemoteDataSource
    private val mNetworkCheck = mock<ICheckNetworkState>()
    private val iValidateRemoteSource = mock<IValidateRemoteSource>()
    private val movieDetailsAPI = mock<MovieDetailsAPI>()


    @Before
    fun init() {
        movieDetailsRemoteDataSource =
            MovieDetailsRemoteDataSource(iValidateRemoteSource, mNetworkCheck, movieDetailsAPI)
    }

    @Test
    fun `when ever there is no internet connection it should return no internet connection error`() =
        runTest {
            whenever(mNetworkCheck.isConnected()).thenReturn(false)
            val expected = movieDetailsRemoteDataSource.getMovieDetails(1)
            val actual =
                Result.ResultNoInternetConnection(error = StringWrapper.FromResource(R.string.no_internet_connection))
            assertEquals(expected, actual)
        }

    @Test
    fun `when ever this is a valid connection , it should return same response obtained from validate response`() =
        runTest {
            val response = MovieDetailsResponse()
            whenever(mNetworkCheck.isConnected()).thenReturn(true)
            whenever(iValidateRemoteSource.validate(movieDetailsAPI.getMoviesDetails(1))).thenReturn(
                Result.ResultSuccess(response)
            )
            val expected = movieDetailsRemoteDataSource.getMovieDetails(1)
            val actual = Result.ResultSuccess(data = response)
            assertEquals(expected, actual)
        }
}