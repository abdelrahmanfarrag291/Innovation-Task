package com.abdelrahman.innovation_task.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.abdelrahman.innovation_task.navigation.screens.MoviesList
import com.abdelrahman.movie_detail_presentation.component.MovieDetailsScreen
import com.abdelrahman.innovation_task.navigation.screens.MovieDetails
import com.abdelrahman.movie_detail_presentation.viewmodel.MovieDetailsContract
import com.abdelrahman.movie_detail_presentation.viewmodel.MovieDetailsViewModel
import com.abdelrahman.movies_list_presentation.ui.MoviesListScreen
import com.abdelrahman.movies_list_presentation.viewmodel.MoviesListViewModel


@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MoviesList) {
        composable<MoviesList> {
            val moviesHome = hiltViewModel<MoviesListViewModel>()
            val state by moviesHome.state.collectAsStateWithLifecycle()
            MoviesListScreen(
                moviesList = state.moviesList ?: listOf(),
                onNavigate = {
                    navController.navigate(MovieDetails(it))
                },
                onEvent = { event ->
                    moviesHome.onEvent(event)
                },
                errorModels = state.errorModels,
                loadingTypes = state.loadingTypes
            )
        }

        composable<MovieDetails> {
            val movieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>()
            val state by movieDetailsViewModel.state.collectAsStateWithLifecycle()
            val id = it.toRoute<MovieDetails>().id
            LaunchedEffect(Unit) {
                movieDetailsViewModel.sendEvent(MovieDetailsContract.MovieDetailsEvents.SendMovieId(id))
            }
            MovieDetailsScreen(
                movieDetailsDTO = state.movieDetailDto,
                loadingTypes = state.loadingTypes,
                errorModels = state.errorModels
            )
        }
    }
}