package com.abdelrahman.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        STATE : State, EVENT : Event, ONE_TIME_ACTION : OneTimeAction> : ViewModel() {

    abstract fun createInitialState(): STATE
    abstract fun onEvent(event: EVENT)

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
        viewModelScope.launch {
            _events.emit(event)
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