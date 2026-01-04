package com.abdelrahman.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.presentation.LoadingTypes
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun <T> PagingComponent(
    modifier: Modifier,
    source: List<T> = listOf(),
    errorModel: ErrorModels? = null,
    loadingTypes: LoadingTypes = LoadingTypes.None,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
    listItem: @Composable (T) -> Unit
) {
    val onLoadMoreState by rememberUpdatedState(onLoadMore)
    val onRefresh by rememberUpdatedState(onRefresh)
    var isRefreshing by remember {
        mutableStateOf(loadingTypes == LoadingTypes.PullToRefreshLoading)
    }
    val pullRefreshState = rememberPullToRefreshState()

    val lazyListState = rememberLazyListState()
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == null) return@collect

                val shouldLoadMore =
                    lastVisibleIndex >= source.size - 2 &&
                            source.isNotEmpty()

                if (shouldLoadMore) {
                    onLoadMoreState()
                }
            }
    }

    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        isRefreshing = isRefreshing,
        state = pullRefreshState,
        onRefresh={
            onRefresh()
        }

    ) {
        if (errorModel != null) {
            PagingError(Modifier.fillMaxSize().align(Alignment.Center), errorModel)
        } else {
            if (loadingTypes == LoadingTypes.FullScreenLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                    LazyColumn(
                        state = lazyListState, modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(source) {
                            listItem(it)
                        }
                        if (loadingTypes == LoadingTypes.PaginationLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
        }

    }
}

@Composable
fun PagingError(modifier: Modifier, errorModels: ErrorModels) {
    Text(
        modifier = modifier,
        text = errorModels.errorMessage.getStringFromWrapper(LocalContext.current)
    )
}