package com.jero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalComposeNavigator: ProvidableCompositionLocal<AppComposeNavigator<KPassScreen>> =
    compositionLocalOf { error("No AppComposeNavigator provided!") }

val currentComposeNavigator: AppComposeNavigator<KPassScreen>
    @Composable
    @ReadOnlyComposable
    get() = LocalComposeNavigator.current
