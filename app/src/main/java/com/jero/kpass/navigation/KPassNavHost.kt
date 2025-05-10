package com.jero.kpass.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jero.navigation.KPassScreen

@Composable
fun KPassNavHost(navHostController: NavHostController) {
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = KPassScreen.SelectDatabase
        ) {
            kpassNavigation()
        }
    }
}