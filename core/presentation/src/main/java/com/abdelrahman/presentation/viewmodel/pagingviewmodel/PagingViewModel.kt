package com.abdelrahman.presentation.viewmodel.pagingviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.presentation.viewmodel.Event
import com.abdelrahman.presentation.viewmodel.OneTimeAction
import com.abdelrahman.presentation.viewmodel.State
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class PagingViewModel<STATE : State, EVENT : Event, ONE_TIME_ACTION : OneTimeAction> :
    ViewModel() {

    private var _pagingState = MutableStateFlow(PagingState(1, 1))
    val pagingState: StateFlow<PagingState> = _pagingState

    abstract fun createInitialState(): STATE
    open fun onEvent(event: Event) {
        when (event) {
            PagingEvents.OnLoadNextPage -> onLoadNextPage()
            PagingEvents.OnPullToRefresh -> {
                updatePagingState(1, _pagingState.value.totalPages)
                onPullToRefresh()
            }
        }
    }

    fun updatePagingState(
        currentPage: Int?,
        totalPages: Int?
    ) {
        _pagingState.update {
            it.copy(
                currentPage = currentPage,
                totalPages = totalPages
            )
        }

    }

    abstract fun onPullToRefresh()

    abstract fun onRequestNextPage(page: Int?)
    private fun onLoadNextPage() {
        val pagingState = _pagingState.value
        val mCurrentPage = pagingState.currentPage ?: 1
        val totalPages = pagingState.totalPages ?: 1
        if (mCurrentPage <= totalPages) {
            onRequestNextPage((pagingState.currentPage ?: 0) + 1)
        }
    }

    private val initialState by lazy {
        createInitialState()
    }

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _events: MutableSharedFlow<EVENT> = MutableSharedFlow()
    val events = _events

    private val _oneTimeAction: Channel<ONE_TIME_ACTION> = Channel()
    val oneTimeAction = _oneTimeAction.receiveAsFlow()

    val currentState: STATE
        get() = state.value

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            events.collect {
                onEvent(it)
            }
        }
    }

    private fun subscribeToOneTimeActions(event: () -> ONE_TIME_ACTION) {
        val uiEvent = event()
        viewModelScope.launch {
            _oneTimeAction.send(uiEvent)
        }
    }

    protected fun sendEvent(event: EVENT) {
        val newEvent = event
        viewModelScope.launch {
            _events.emit(newEvent)
        }
    }

    protected fun setState(reduce: STATE.() -> STATE) {
        val newState = currentState.reduce()
        _state.value = newState
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}