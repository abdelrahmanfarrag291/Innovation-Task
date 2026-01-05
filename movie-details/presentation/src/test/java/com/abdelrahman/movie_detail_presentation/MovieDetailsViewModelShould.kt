package com.abdelrahman.movie_detail_presentation

import app.cash.turbine.test
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movie_detail_presentation.viewmodel.MovieDetailsContract
import com.abdelrahman.movie_detail_presentation.viewmodel.MovieDetailsViewModel
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO
import com.abdelrahman.movie_details_domain.repository.IGetMovieDetailsRepository
import com.abdelrahman.presentation.LoadingTypes
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelShould {

    //SUT
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val movieDetailsRepository = mock<IGetMovieDetailsRepository>()

    @Before
    fun init() {
        val dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        movieDetailsViewModel = MovieDetailsViewModel(movieDetailsRepository)
        movieDetailsViewModel.sendEvent(MovieDetailsContract.MovieDetailsEvents.SendMovieId(42))
    }

    @Test
    fun `whenever call getMovieDetails return success it should update the movie details state with MovieDetailsDTO`() =
        runTest {
            val movieDetailsDto = MovieDetailsDTO()
            whenever(movieDetailsRepository.getMovieDetails(42)).thenReturn(flow {
                emit(DataState.DataSuccess(result = movieDetailsDto))
            })
            //   advanceUntilIdle()
            movieDetailsViewModel.sendEvent(MovieDetailsContract.MovieDetailsEvents.GetMovieDetails)
            advanceUntilIdle()
            val actual = movieDetailsViewModel.currentState.movieDetailDto
            assertEquals(movieDetailsDto, actual)
        }

    @Test
    fun `whenever call getMovieDetails returns failure it should update errorModels with error obtained`() =
        runTest {
            whenever(movieDetailsRepository.getMovieDetails(42)).thenReturn(
                flow {
                    emit(
                        DataState.DataError(errorModels = ErrorModels.NoInternetConnectionError)
                    )
                }
            )
            movieDetailsViewModel.sendEvent(MovieDetailsContract.MovieDetailsEvents.GetMovieDetails)
            advanceUntilIdle()
            val actual = movieDetailsViewModel.currentState.errorModels
            val expected = ErrorModels.NoInternetConnectionError
            assertEquals(expected, actual)
        }

    @Test
    fun `whenever call getMovieDetails the loadingTypes should be toggled from none to FullScreen and then none`() =
        runTest {
            whenever(movieDetailsRepository.getMovieDetails(42)).thenReturn(
                flow {
                    emit(
                        DataState.DataError(errorModels = ErrorModels.NoInternetConnectionError)
                    )
                }
            )
            movieDetailsViewModel.sendEvent(MovieDetailsContract.MovieDetailsEvents.GetMovieDetails)
            movieDetailsViewModel.state.test {
                val initialEmission = awaitItem()
                assertEquals(LoadingTypes.None, initialEmission.loadingTypes)
                val secondEmission = awaitItem()
                assertEquals(LoadingTypes.None, secondEmission.loadingTypes)
                val thirdEmission = awaitItem()
                assertEquals(LoadingTypes.FullScreenLoading, thirdEmission.loadingTypes)
                val fourthEmission = awaitItem()
                assertEquals(LoadingTypes.None, fourthEmission.loadingTypes)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}