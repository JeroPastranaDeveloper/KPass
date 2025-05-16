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
            UiIntent.CreateDatabase -> dispatchAction(UiAction.CreateDatabase)
            UiIntent.DoBiometricAuthentication -> doBiometricAuthentication()
            UiIntent.SelectDatabase -> dispatchAction(UiAction.SelectDatabase)
            UiIntent.SetupBiometricAuthentication -> setState {
                copy(
                    canDoBiometricAuthentication = preferencesHandler.databaseUri.orEmpty().isNotEmpty()
                )
            }
            is UiIntent.SyncWithFileUri -> saveDatabaseUri(intent.uri)
        }
    }

    init {
        doBiometricAuthentication()
    }

    private fun doBiometricAuthentication() {
        val canDoBiometricAuthentication = preferencesHandler.databaseUri.orEmpty().isNotEmpty()
        setState {
            copy(
                canDoBiometricAuthentication = canDoBiometricAuthentication
            )
        }
        if (preferencesHandler.databaseUri.orEmpty().isNotEmpty()) {
            dispatchAction(UiAction.DoBiometricAuthentication(goToAccountsScreen = {
                dispatchAction(UiAction.GoToAccountsScreen)
            }))
        }
    }

    private fun saveDatabaseUri(uri: String) {
        preferencesHandler.databaseUri = uri
        dispatchAction(UiAction.GoToAccountsScreen)
    }
}
