package com.jero.kpass.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.jero.designsystem.theme.KPassTheme
import com.jero.navigation.AppComposeNavigator
import com.jero.navigation.KPassScreen
import com.jero.kpass.navigation.KPassNavHost

@Composable
fun KPassMain(composeNavigator: AppComposeNavigator<KPassScreen>) {
    KPassTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        KPassNavHost(navHostController = navHostController)
    }
}