package com.abdelrahman.movie_details_data.repository

import com.abdelrahman.data.mapper.mapToDataState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.movie_details_data.mappers.asMovieDetailsDto
import com.abdelrahman.movie_details_data.remote.models.MovieDetailsResponse
import com.abdelrahman.movie_details_data.remote.remote_datasource.IMovieDetailsRemoteDataSource
import com.abdelrahman.movie_details_domain.repository.IGetMovieDetailsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MovieDetailsRepositoryShould {

    //SUT
    private lateinit var movieDetailsRepository: IGetMovieDetailsRepository
    private val movieDetailsRemoteDataSource = mock<IMovieDetailsRemoteDataSource>()

    @Before
    fun init() {
        movieDetailsRepository = MovieDetailsRepository(movieDetailsRemoteDataSource)
    }

    @Test
    fun `when get data from remote data source it should map to do domain data state and emit it as flow`() =
        runTest {
            val movieDetails = MovieDetailsResponse()
            val response = Result.ResultSuccess(movieDetails)
            whenever(movieDetailsRemoteDataSource.getMovieDetails(1)).thenReturn(response)
            val expected = movieDetailsRepository.getMovieDetails(1).first()
            val actual = Result.ResultSuccess(movieDetails).mapToDataState {
                it.asMovieDetailsDto()
            }.first()
            assertEquals(expected, actual)
        }
}