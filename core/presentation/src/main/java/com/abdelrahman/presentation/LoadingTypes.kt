package com.abdelrahman.presentation

sealed interface LoadingTypes {
    data object None : LoadingTypes
    data object FullScreenLoading : LoadingTypes
    data object PullToRefreshLoading : LoadingTypes
    data object PaginationLoading : LoadingTypes
}