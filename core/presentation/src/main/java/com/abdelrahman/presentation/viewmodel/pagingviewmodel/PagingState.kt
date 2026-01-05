package com.abdelrahman.presentation.viewmodel.pagingviewmodel

import com.abdelrahman.presentation.viewmodel.State

data class PagingState(
    val currentPage: Int? = null,
    val totalPages: Int? = null
) : State