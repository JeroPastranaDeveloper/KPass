package com.jero.designsystem.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.OverlayClip
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize
import androidx.compose.animation.SharedTransitionScope.SharedContentState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

// Modifier base reutilizable en todos los casos
context(SharedTransitionScope)
fun Modifier.kpassSharedElement(
    isLocalInspectionMode: Boolean,
    state: SharedContentState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform = DefaultBoundsTransform,
    placeHolderSize: PlaceHolderSize = PlaceHolderSize.contentSize,
    renderInOverlayDuringTransition: Boolean = true,
    zIndexInOverlay: Float = 0f,
    clipInOverlayDuringTransition: OverlayClip = ParentClip,
): Modifier =
    if (isLocalInspectionMode) {
        this
    } else {
        this.sharedElement(
            sharedContentState = state,
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = boundsTransform,
            placeHolderSize = placeHolderSize,
            renderInOverlayDuringTransition = renderInOverlayDuringTransition,
            zIndexInOverlay = zIndexInOverlay,
            clipInOverlayDuringTransition = clipInOverlayDuringTransition,
        )
    }

// Reutilizable para Texts con un boundsTransform más suave
context(SharedTransitionScope)
fun Modifier.kpassSharedElementForText(
    isLocalInspectionMode: Boolean,
    state: SharedContentState,
    animatedVisibilityScope: AnimatedVisibilityScope,
): Modifier = this.kpassSharedElement(
    isLocalInspectionMode = isLocalInspectionMode,
    state = state,
    animatedVisibilityScope = animatedVisibilityScope,
    boundsTransform = TextBoundsTransform,
    placeHolderSize = PlaceHolderSize.contentSize,
)

// Clip de overlay por defecto
private val ParentClip: OverlayClip =
    object : OverlayClip {
        override fun getClipPath(
            state: SharedContentState,
            bounds: Rect,
            layoutDirection: LayoutDirection,
            density: Density,
        ): Path? = state.parentSharedContentState?.clipPathInOverlay
    }

// Spring por defecto
private val DefaultSpring = spring(
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = Rect.VisibilityThreshold,
)

val DefaultBoundsTransform = BoundsTransform { _, _ -> DefaultSpring }

// Spring más suave para textos
private val TextSpring = spring(
    stiffness = Spring.StiffnessLow,
    dampingRatio = Spring.DampingRatioNoBouncy,
    visibilityThreshold = Rect.VisibilityThreshold,
)

val TextBoundsTransform = BoundsTransform { _, _ -> TextSpring }
