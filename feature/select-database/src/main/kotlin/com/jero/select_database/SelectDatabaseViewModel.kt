package com.jero.select_database

import android.net.Uri
import androidx.core.net.toUri
import com.jero.core.viewmodel.BaseViewModelWithActions
import com.jero.domain.preferences.PreferencesHandler
import com.jero.domain.session.SessionManager
import com.jero.select_database.SelectDatabaseViewContract.UiAction
import com.jero.select_database.SelectDatabaseViewContract.UiIntent
import com.jero.select_database.SelectDatabaseViewContract.UiState

class SelectDatabaseViewModel(
    private val preferencesHandler: PreferencesHandler,
    private val sessionManager: SessionManager,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {
    private var isCreation: Boolean = false

    override val initialViewState = UiState()
    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.AskPasswordForUri -> askPasswordForUri(
                intent.uri,
                intent.isCreation
            )

            UiIntent.CreateDatabase -> dispatchAction(UiAction.CreateDatabase)
            UiIntent.DoBiometricAuthentication -> doBiometricAuthentication()
            is UiIntent.SetPasswordInRAM -> setPasswordInRAM(intent.password)
            UiIntent.SelectDatabase -> dispatchAction(UiAction.SelectDatabase)
            UiIntent.SetupBiometricAuthentication -> doBiometricAuthentication()
            UiIntent.GoToAccountsScreen -> goToAccountsScreen()
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
        if (canDoBiometricAuthentication) {
            dispatchAction(UiAction.DoBiometricAuthentication(showDatabasePasswordDialog = {
                setState { copy(showDatabasePasswordDialog = true) }
            }))
        }
    }

    private fun goToAccountsScreen() {
        dispatchAction(UiAction.GoToAccountsScreen)
    }

    private fun setPasswordInRAM(password: String) {
        sessionManager.databasePassword = password
        val databaseUri = preferencesHandler.databaseUri.orEmpty().toUri()
        setState { copy(databasePassword = password, showDatabasePasswordDialog = false) }

        if (isCreation) {
            dispatchAction(UiAction.RequestPasswordForCreation(databaseUri))
        } else {
            dispatchAction(UiAction.RequestPasswordForOpening(databaseUri))
        }
    }

    private fun askPasswordForUri(uri: Uri, isCreation: Boolean) {
        preferencesHandler.databaseUri = uri.toString()
        this.isCreation = isCreation
        setState { copy(showDatabasePasswordDialog = true) }
    }
}
