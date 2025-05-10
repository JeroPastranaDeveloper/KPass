package com.jero.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

class KPassComposeNavigator: AppComposeNavigator<KPassScreen>() {

    override fun navigate(route: KPassScreen, optionsBuilder: (NavOptionsBuilder.() -> Unit)?) {
        val options = optionsBuilder?.let { navOptions(it) }
        navigationCommands.tryEmit(ComposeNavigationCommand.NavigateToRoute(route, options))
    }

    override fun navigateAndClearBackStack(route: KPassScreen) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateToRoute(
                route,
                navOptions {
                    popUpTo(0)
                }
            )
        )
    }

    override fun popUpTo(route: KPassScreen, inclusive: Boolean) {
        navigationCommands.tryEmit(ComposeNavigationCommand.PopUpToRoute(route, inclusive))
    }

    override fun <R> navigateBackWithResult(key: String, result: R, route: KPassScreen?) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateUpWithResult(
                key = key,
                result = result,
                route = route,
            )
        )
    }
}
