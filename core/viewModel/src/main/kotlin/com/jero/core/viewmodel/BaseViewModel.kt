package com.jero.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, I> : ViewModel(), ViewModelIntentSender<I> {

    private val _state: MutableStateFlow<S> by lazy { MutableStateFlow(initialViewState) }
    val state: StateFlow<S> get() = _state

    private val vmIntent = Channel<I>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            vmIntent.consumeAsFlow().collect { intent ->
                manageIntent(intent)
            }
        }
    }

    protected abstract val initialViewState: S

    protected fun setState(newState: S.() -> S) {
        _state.update(newState)
    }

    protected abstract suspend fun manageIntent(intent: I)

    override fun sendIntent(intent: I) {
        viewModelScope.launch {
            vmIntent.send(intent)
        }
    }
}
