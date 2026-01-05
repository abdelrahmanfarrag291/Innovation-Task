package com.abdelrahman.movie_detail_presentation.viewmodel

import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.viewmodel.Event
import com.abdelrahman.presentation.viewmodel.OneTimeAction
import com.abdelrahman.presentation.viewmodel.State

class MovieDetailsContract {

    data class MovieDetailsState(
        val id : Int?=null,
        val loadingTypes: LoadingTypes= LoadingTypes.None,
        val movieDetailDto : MovieDetailsDTO ?=null,
        val errorModels : ErrorModels?=null
    ): State


    sealed interface MovieDetailsEvents : Event{
        data class SendMovieId(val id: Int) : MovieDetailsEvents
        data object GetMovieDetails : MovieDetailsEvents
    }
    sealed interface MovieDetailsOneTimeAction : OneTimeAction
}