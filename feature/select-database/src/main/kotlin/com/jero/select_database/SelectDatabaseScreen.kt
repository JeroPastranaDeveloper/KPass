package com.jero.select_database

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jero.core.designsystem.R
import com.jero.designsystem.components.KPassAppBar

@Composable
fun SharedTransitionScope.SelectDatabaseScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    // viewModel: HomeViewModel = koinViewModel()
) {
    SetStatusBarIconsColor()
    Scaffold(
        topBar = { KPassAppBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_qr_code_24),
                contentDescription = null,
                modifier = Modifier.padding(vertical = 32.dp)
            )
        }
    }
}

@Composable
private fun SetStatusBarIconsColor() {

    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = false
    )
}