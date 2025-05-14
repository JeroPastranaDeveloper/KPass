package com.jero.core.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModelWithActions<S, I, A> : BaseViewModel<S, I>() {

    private val pendingActions: MutableList<A> = mutableListOf()

    private val _actions: MutableSharedFlow<A> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val actions: Flow<A> get() = _actions

    init {
        viewModelScope.launch {
            _actions.subscriptionCount.collect { count ->
                if (count > 0 && pendingActions.isNotEmpty()) {
                    pendingActions.forEach(::dispatchAction)
                    pendingActions.clear()
                }
            }
        }
    }

    protected fun dispatchAction(action: A) {
        if (_actions.hasSubscribers) {
            _actions.tryEmit(action)
        } else {
            pendingActions.add(action)
        }
    }

    private val <T> MutableSharedFlow<T>.hasSubscribers: Boolean
        get() = subscriptionCount.value > 0
}
