package com.jero.navigation

import org.koin.dsl.module

val navigationModule = module {
    single<AppComposeNavigator<KPassScreen>> {
        KPassComposeNavigator()
    }
}
