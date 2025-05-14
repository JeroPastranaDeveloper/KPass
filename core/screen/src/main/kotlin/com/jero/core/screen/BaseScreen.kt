package com.jero.core.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun <A> HandleActions(
    actions: Flow<A>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    handle: (A) -> Unit
) {
    val lifecycle = lifecycleOwner.lifecycle
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(lifecycle) {
        val job = coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                actions.collect { action ->
                    handle(action)
                }
            }
        }
        onDispose { job.cancel() }
    }
}

@Composable
fun SetStatusBarIconsColor() {

    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = false
    )
}
