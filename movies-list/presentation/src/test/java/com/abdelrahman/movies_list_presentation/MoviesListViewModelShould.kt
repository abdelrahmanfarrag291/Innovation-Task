package com.abdelrahman.movies_list_presentation

import app.cash.turbine.test
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movies_list_domain.entity.Movie
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import com.abdelrahman.movies_list_domain.repository.IMoviesRepository
import com.abdelrahman.movies_list_presentation.viewmodel.MoviesListViewModel
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.viewmodel.pagingviewmodel.PagingEvents
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class MoviesListViewModelShould {
    //SUT
    private lateinit var moviesListViewModel: MoviesListViewModel
    private val moviesListRepository = mock<IMoviesRepository>()

    @Before
    fun init() {
        val dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        moviesListViewModel = MoviesListViewModel(moviesListRepository)
    }

    @Test
    fun `whenever call getMovies and there is items in repository , it should return the result`() =
        runTest {
            val movieDTO = MoviesDTO(1, 2, arrayListOf(Movie(), Movie()))
            whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
                emit(DataState.DataSuccess(result = movieDTO))
            })
            advanceUntilIdle()
            val actual = moviesListViewModel.currentState.moviesList
            val pagingState = moviesListViewModel.pagingState.first()
            //List is added
            assertEquals(movieDTO.moviesList, actual)
            assertEquals(1, pagingState.currentPage)
            assertEquals(2, pagingState.totalPages)
        }

    @Test
    fun `whenever call getMovies and there no item either save nor remote is responding with no internet connection, it should return error of no internet connection`() =
        runTest {
            whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
                emit(DataState.DataError(errorModels = ErrorModels.NoInternetConnectionError))
            })
            advanceUntilIdle()
            val actual = moviesListViewModel.currentState.errorModels
            assertEquals(ErrorModels.NoInternetConnectionError, actual)
        }

    @Test
    fun `whenever call getMovies and request pagination new list must be appended to the previous list`() =
        runTest {
            val movieDTO = MoviesDTO(1, 2, arrayListOf(Movie()))
            val movieDTO2 = MoviesDTO(2, 2, arrayListOf(Movie()))
            whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
                emit(DataState.DataSuccess(result = movieDTO))
            })
            whenever(moviesListRepository.getMovies(2)).thenReturn(flow {
                emit(DataState.DataSuccess(result = movieDTO2))
            })
            advanceUntilIdle()
            val actual1 = moviesListViewModel.currentState.moviesList
            val pagingState1 = moviesListViewModel.pagingState.first()

            assertEquals(listOf(Movie()), actual1)
            assertEquals(1, pagingState1.currentPage)
            assertEquals(2, pagingState1.totalPages)
            moviesListViewModel.onEvent(PagingEvents.OnLoadNextPage)
            advanceUntilIdle()
            val actual2 = moviesListViewModel.currentState.moviesList
            val pagingState2 = moviesListViewModel.pagingState.first()
            assertEquals(listOf(Movie(), Movie()), actual2)
            assertEquals(2, pagingState2.currentPage)
            assertEquals(2, pagingState2.totalPages)
        }

    @Test
    fun `whenever call getMovies with pull to refresh , it must reset the page and paging state`() =
        runTest {
            val movieDTO = MoviesDTO(1, 2, arrayListOf(Movie(), Movie()))
            whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
                emit(DataState.DataSuccess(result = movieDTO))
            })
            advanceUntilIdle()
            moviesListViewModel.onEvent(PagingEvents.OnPullToRefresh)
            val actual = moviesListViewModel.currentState
            val pagingState1 = moviesListViewModel.pagingState.first()
            assertEquals(listOf<Movie>(), actual.moviesList)
            assertEquals(null, pagingState1.currentPage)
            assertEquals(null, pagingState1.totalPages)
            advanceUntilIdle()
            val actual2 = moviesListViewModel.currentState
            val pagingState2 = moviesListViewModel.pagingState.first()
            assertEquals(arrayListOf(Movie(), Movie()), actual2.moviesList)
            assertEquals(1, pagingState2.currentPage)
            assertEquals(2, pagingState2.totalPages)
        }

    @Test
    fun `loadingTypes toggle as expected in case of first open`() = runTest {
        val movieDTO = MoviesDTO(1, 2, arrayListOf(Movie(), Movie()))
        whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
            emit(DataState.DataSuccess(result = movieDTO))
        })
        moviesListViewModel.state.test {
            val firstEmission = awaitItem()
            assertEquals(LoadingTypes.None, firstEmission.loadingTypes)
            val secondEmission = awaitItem()
            assertEquals(LoadingTypes.FullScreenLoading, secondEmission.loadingTypes)
            val thirdEmission = awaitItem()
            assertEquals(LoadingTypes.None, thirdEmission.loadingTypes)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadingTypes toggle as expected in case of pagination requested`() = runTest {
        val movieDTO = MoviesDTO(1, 2, arrayListOf(Movie()))
        val movieDTO2 = MoviesDTO(2, 2, arrayListOf(Movie()))
        whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
            emit(DataState.DataSuccess(result = movieDTO))
        })
        whenever(moviesListRepository.getMovies(2)).thenReturn(flow {
            emit(DataState.DataSuccess(result = movieDTO2))
        })
        advanceUntilIdle()
        moviesListViewModel.onEvent(PagingEvents.OnLoadNextPage)
        moviesListViewModel.state.test {
            val firstEmission = awaitItem()
            assertEquals(LoadingTypes.None, firstEmission.loadingTypes)
            val thirdEmission = awaitItem()
            assertEquals(LoadingTypes.PaginationLoading, thirdEmission.loadingTypes)
            val fourthEmission = awaitItem()
            assertEquals(LoadingTypes.None, fourthEmission.loadingTypes)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `loadingTypes toggle as expected in case of pull to refresh  requested`() = runTest {
        val movieDTO = MoviesDTO(1, 2, arrayListOf(Movie()))
        whenever(moviesListRepository.getMovies(1)).thenReturn(flow {
            emit(DataState.DataSuccess(result = movieDTO))
        })

        advanceUntilIdle()
        moviesListViewModel.onEvent(PagingEvents.OnPullToRefresh)
        moviesListViewModel.state.test {
            val firstEmission = awaitItem()
            assertEquals(LoadingTypes.None, firstEmission.loadingTypes)
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