package com.abdelrahman.innovation_task.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abdelrahman.innovation_task.navigation.screens.MoviesList
import com.abdelrahman.movie_detail_presentation.route.MovieDetails
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
            val moviesHome = hiltViewModel<MovieDetailsViewModel>()
            val state by moviesHome.state.collectAsStateWithLifecycle()
            Box(modifier = Modifier.fillMaxSize()) {
                Text("welcome to details screen ")
            }
        }
    }
}