package com.abdelrahman.movie_detail_presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.abdelrahman.domain.models.DataState
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movie_detail_presentation.route.MovieDetails
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO
import com.abdelrahman.movie_details_domain.repository.IGetMovieDetailsRepository
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val iGetMovieDetailsRepository: IGetMovieDetailsRepository,
    private val mSavedStateHandle: SavedStateHandle
) : BaseViewModel<MovieDetailsContract.MovieDetailsState, MovieDetailsContract.MovieDetailsEvents, MovieDetailsContract.MovieDetailsOneTimeAction>() {

    init {
        sendEvent(MovieDetailsContract.MovieDetailsEvents.PageOpened)
    }

    override fun createInitialState(): MovieDetailsContract.MovieDetailsState {
        return MovieDetailsContract.MovieDetailsState()
    }

    override fun onEvent(event: MovieDetailsContract.MovieDetailsEvents) {
        when (event) {
            MovieDetailsContract.MovieDetailsEvents.PageOpened -> onPageOpened()
        }
    }

    private fun onPageOpened() {
        val movieId = mSavedStateHandle.toRoute<MovieDetails>()
        movieId.id.let {
            viewModelScope.launch {
                setState {
                    copy(
                        loadingTypes = LoadingTypes.FullScreenLoading
                    )
                }
                iGetMovieDetailsRepository.getMovieDetails(it).collect { result ->
                    when (result) {
                        is DataState.DataError -> onMovieDetailsError(result.errorModels)
                        is DataState.DataSuccess<MovieDetailsDTO> -> onMovieDetailsSuccess(result.result)
                    }
                }

            }
        }
    }

    private fun onMovieDetailsSuccess(result: MovieDetailsDTO) {
        setState {
            copy(
                movieDetailDto = result,
                loadingTypes = LoadingTypes.None,
                errorModels = null
            )
        }
    }

    private fun onMovieDetailsError(errorModels: ErrorModels) {
        setState {
            copy(
                errorModels = errorModels,
                loadingTypes = LoadingTypes.None
            )
        }
    }
}