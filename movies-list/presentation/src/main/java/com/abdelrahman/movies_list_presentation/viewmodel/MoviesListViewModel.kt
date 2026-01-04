package com.abdelrahman.movies_list_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import com.abdelrahman.movies_list_domain.repository.IMoviesRepository
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.viewmodel.Event
import com.abdelrahman.presentation.viewmodel.pagingviewmodel.PagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val iMoviesRepository: IMoviesRepository
) : PagingViewModel<MoviesListContract.MoviesState, MoviesListContract.MoviesEvents, MoviesListContract.MoviesOneTimeAction>() {

    init {
        sendEvent(MoviesListContract.MoviesEvents.OnPageOpened)
    }

    override fun createInitialState(): MoviesListContract.MoviesState {
        return MoviesListContract.MoviesState()
    }

    override fun onEvent(event: Event) {
        super.onEvent(event)
        when (event) {
            MoviesListContract.MoviesEvents.OnPageOpened -> onPageOpened()
        }
    }

    private fun onPageOpened() {
        setState {
            copy(
                loadingTypes = LoadingTypes.FullScreenLoading
            )
        }
        viewModelScope.launch {
            iMoviesRepository.getMovies(1).collect { state ->
                when (state) {
                    is DataState.DataError -> setState {
                        copy(
                            loadingTypes = LoadingTypes.None,
                            errorModels = state.errorModels
                        )
                    }

                    is DataState.DataSuccess<MoviesDTO> -> onDataSuccess(state.result)
                }
            }
        }
    }


    private fun onLoadNextPage(page: Int?) {
        viewModelScope.launch {
            setState {
                copy(
                    loadingTypes = LoadingTypes.PaginationLoading
                )
            }
            iMoviesRepository.getMovies(page ?: 1).collect { state ->
                when (state) {
                    is DataState.DataError -> setState {
                        copy(
                            loadingTypes = LoadingTypes.None,
                            errorModels = null
                        )
                    }

                    is DataState.DataSuccess<MoviesDTO> -> onDataSuccess(state.result)
                }
            }
        }
    }

    private fun onDataSuccess(result: MoviesDTO) {
        updatePagingState(
            currentPage = result.currentPage,
            totalPages = result.totalPages
        )
        setState {
            copy(
                moviesList = currentState.moviesList?.apply {
                    addAll(result.moviesList ?: listOf())
                },
                loadingTypes = LoadingTypes.None,
                errorModels = null
            )
        }
    }

    override fun onPullToRefresh() {
        if (currentState.loadingTypes == LoadingTypes.None) {
            setState {
                copy(
                    moviesDTO = null,
                    moviesList = arrayListOf()
                )
            }
            sendEvent(MoviesListContract.MoviesEvents.OnPageOpened)
        }
    }

    override fun onRequestNextPage(page: Int?) {
        if (currentState.loadingTypes == LoadingTypes.None) {
            onLoadNextPage(page)
        }
    }
}