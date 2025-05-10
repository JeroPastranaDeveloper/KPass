package com.jero.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

private val LocalColors = compositionLocalOf<KPassColors> {
    error("No colors provided! Make sure to wrap all usages of KPass components in KPassTheme.")
}

@Composable
fun KPassTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: KPassColors = if (darkTheme) {
        KPassColors.Companion.defaultDarkColors()
    } else {
        KPassColors.Companion.defaultLightColors()
    },
    background: KPassBackground = KPassBackground.defaultBackground(darkTheme),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalBackgroundTheme provides background,
    ) {
        Box(
            modifier = Modifier.background(background.color)
        ) {
            content()
        }
    }
}

/**
 * Contains ease-of-use accessors for different properties used to style and customize the app
 * look and feel.
 */

object KPassTheme {
    val colors: KPassColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val background: KPassBackground
        @Composable
        @ReadOnlyComposable
        get() = LocalBackgroundTheme.current
}
