package com.jero.kpass.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jero.add_edit_account.AddEditAccountScreen
import com.jero.home.AccountsScreen
import com.jero.login.KPassHome
import com.jero.navigation.KPassScreen
import com.jero.select_database.SelectDatabaseScreen

context(SharedTransitionScope)
fun NavGraphBuilder.kpassNavigation() {
    composable<KPassScreen.Home> {
        KPassHome(this)
    }

    composable<KPassScreen.SelectDatabase> {
        SelectDatabaseScreen(this)
    }

    composable<KPassScreen.Accounts> {
        AccountsScreen(this)
    }

    composable<KPassScreen.AddEditAccount>(
        typeMap = KPassScreen.AddEditAccount.typeMap
    ) {
        AddEditAccountScreen(this)
    }
}