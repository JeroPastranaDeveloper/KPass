package com.jero.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jero.core.designsystem.R

@Immutable
data class KPassBackground(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
) {
    companion object {
        @Composable
        fun defaultBackground(darkTheme: Boolean): KPassBackground =
            if (darkTheme) {
                KPassBackground(
                    color = colorResource(id = R.color.dark),
                    tonalElevation = 0.dp,
                )
            } else {
                KPassBackground(
                    color = colorResource(id = R.color.white),
                    tonalElevation = 0.dp,
                )
            }
        }
    }

val LocalBackgroundTheme: ProvidableCompositionLocal<KPassBackground> =
    staticCompositionLocalOf { KPassBackground() }
