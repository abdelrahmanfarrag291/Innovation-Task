package com.abdelrahman.movies_list_presentation.viewmodel

import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movies_list_domain.entity.Movie
import com.abdelrahman.movies_list_domain.entity.MoviesDTO
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.viewmodel.Event
import com.abdelrahman.presentation.viewmodel.OneTimeAction
import com.abdelrahman.presentation.viewmodel.State
import com.abdelrahman.presentation.viewmodel.pagingviewmodel.PagingEvents
import com.abdelrahman.presentation.viewmodel.pagingviewmodel.PagingState

object MoviesListContract {

    data class MoviesState(
        val moviesDTO: MoviesDTO? = null,
        val moviesList: MutableList<Movie>? = mutableListOf(),
        val loadingTypes: LoadingTypes = LoadingTypes.FullScreenLoading,
        val errorModels: ErrorModels? = null
    ) : State

    sealed interface MoviesEvents : Event {
        data object OnPageOpened : MoviesEvents
    }

    sealed interface MoviesOneTimeAction : OneTimeAction
}