package com.jero.kpass.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jero.login.KPassHome
import com.jero.select_database.SelectDatabaseScreen
import com.jero.navigation.KPassScreen

context(SharedTransitionScope)
fun NavGraphBuilder.kpassNavigation() {
    composable<KPassScreen.Home> {
        KPassHome(this)
    }

    composable<KPassScreen.SelectDatabase> {
        SelectDatabaseScreen(this)
    }

    // TODO: Para añadir otra pantalla; cada pantalla es un submódulo de :feature
    /*composable<NewsScreen.Details>(
        typeMap = KPassScreen.Details.typeMap
    ) {
        NewDetailScreen(this)
    }*/
}