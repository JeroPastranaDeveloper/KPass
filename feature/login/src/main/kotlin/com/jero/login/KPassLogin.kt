package com.jero.login

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jero.designsystem.components.KPassAppBar

@Composable
fun SharedTransitionScope.KPassHome(
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
            Text(
                text = "KPass",
                modifier = Modifier.padding(vertical = 32.dp)
            )

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Username") },
                modifier = Modifier.padding(vertical = 16.dp)
            )

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                modifier = Modifier.padding(16.dp)
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
