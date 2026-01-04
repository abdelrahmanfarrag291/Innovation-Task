package com.abdelrahman.innovation_task.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abdelrahman.innovation_task.navigation.screens.MoviesList
import com.abdelrahman.movies_list_presentation.ui.MoviesListScreen
import com.abdelrahman.movies_list_presentation.viewmodel.MoviesListViewModel
import com.abdelrahman.presentation.LoadingTypes


@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = MoviesList) {
        composable<MoviesList> {
            val moviesHome = hiltViewModel<MoviesListViewModel>()
            val state by moviesHome.state.collectAsStateWithLifecycle()
            MoviesListScreen(
                moviesList = state.moviesList?:listOf(),
                onEvent = { event ->
                    moviesHome.onEvent(event)
                },
                errorModels = state.errorModels,
                loadingTypes = state.loadingTypes
            )
        }
    }
}