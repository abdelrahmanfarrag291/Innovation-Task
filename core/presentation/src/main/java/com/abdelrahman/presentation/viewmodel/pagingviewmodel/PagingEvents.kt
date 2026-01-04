package com.abdelrahman.presentation.viewmodel.pagingviewmodel

import com.abdelrahman.presentation.viewmodel.Event

sealed interface PagingEvents : Event {
    data object OnLoadNextPage : PagingEvents
    data object OnPullToRefresh : PagingEvents
}