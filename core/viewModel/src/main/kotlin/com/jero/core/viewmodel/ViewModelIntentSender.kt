package com.jero.core.viewmodel

fun interface ViewModelIntentSender<I> {
    fun sendIntent(intent: I)
}
