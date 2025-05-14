package com.jero.select_database

import com.example.domain.preferences.PreferencesHandler
import com.jero.core.viewmodel.BaseViewModelWithActions
import com.jero.select_database.SelectDatabaseViewContract.UiState
import com.jero.select_database.SelectDatabaseViewContract.UiAction
import com.jero.select_database.SelectDatabaseViewContract.UiIntent

class SelectDatabaseViewModel(
    private val preferencesHandler: PreferencesHandler
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()
    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.SelectDatabase -> dispatchAction(UiAction.SelectDatabase)
            is UiIntent.SyncWithFileUri -> saveDatabaseUri(intent.uri)
            UiIntent.GoToAccountsScreen -> checkDatabase()
            UiIntent.CreateDatabase -> dispatchAction(UiAction.CreateDatabase)
        }
    }

    init {
        checkDatabase()
    }

    private fun checkDatabase() {
        if (preferencesHandler.databaseUri.orEmpty().isNotEmpty()) {
            dispatchAction(UiAction.GoToAccountsScreen)
        }
    }

    private fun saveDatabaseUri(uri: String) {
        preferencesHandler.databaseUri = uri
        dispatchAction(UiAction.GoToAccountsScreen)
    }
}
