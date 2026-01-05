package com.abdelrahman.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.R
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
    val onRefresh by rememberUpdatedState(onRefresh)
    var isRefreshing by remember {
        mutableStateOf(loadingTypes == LoadingTypes.PullToRefreshLoading)
    }
    val pullRefreshState = rememberPullToRefreshState()

    val lazyListState = rememberLazyListState()
    val onLoadMoreState by rememberUpdatedState(onLoadMore)

    LaunchedEffect(lazyListState, source.size) {
        snapshotFlow {
            Triple(
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index,
                lazyListState.layoutInfo.totalItemsCount,
                source.size
            )
        }
            .distinctUntilChanged()
            .collect { (lastVisibleIndex, _, size) ->
                if (lastVisibleIndex == null || size == 0) return@collect
                val shouldLoadMore =
                    lastVisibleIndex >= size - 2

                if (shouldLoadMore && !isRefreshing && loadingTypes == LoadingTypes.None) {
                    onLoadMoreState()
                }
            }
    }


    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            lazyListState.scrollToItem(0)
        }
    }
    if (errorModel != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            errorModel.ErrorComponent(Modifier.wrapContentSize())
        }
    } else {
        if (loadingTypes == LoadingTypes.FullScreenLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            PullToRefreshBox(
                modifier = modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                isRefreshing = isRefreshing,
                state = pullRefreshState,
                onRefresh = {
                    if (source.isNotEmpty())
                        onRefresh()
                }
            ) {
                LazyColumn(
                    state = lazyListState, modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(dimensionResource(R.dimen.dimen_16)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_8))
                ) {
                    items(source) {
                        listItem(it)
                    }
                    if (loadingTypes == LoadingTypes.PaginationLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.dimen_16)),
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
