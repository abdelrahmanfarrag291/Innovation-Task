package com.abdelrahman.movies_list_presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movies_list_domain.entity.Movie
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.components.PagingComponent
import com.abdelrahman.presentation.viewmodel.Event
import com.abdelrahman.presentation.viewmodel.pagingviewmodel.PagingEvents

@Composable
fun MoviesListScreen(
    moviesList: List<Movie> = listOf(),
    errorModels: ErrorModels?,
    loadingTypes: LoadingTypes,
    onNavigate : (Int)-> Unit ={},
    onEvent: (Event) -> Unit = {}
) {
    val event by rememberUpdatedState(onEvent)
    PagingComponent(
        modifier = Modifier,
        loadingTypes = loadingTypes,
        errorModel = errorModels,
        onLoadMore = {
            event(PagingEvents.OnLoadNextPage)
        },
        onRefresh = {
            event(PagingEvents.OnPullToRefresh)
        },
        source = moviesList
    ) { movie ->
        MoviesListItem(modifier = Modifier.clickable(onClick ={
            onNavigate(movie.movieId?:-1)
        }),movie = movie)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview_MoviesListScreen() {
    //  MoviesListScreen()
}