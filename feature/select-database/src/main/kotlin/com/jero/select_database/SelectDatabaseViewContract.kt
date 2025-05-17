package com.jero.select_database

import android.net.Uri
import com.jero.core.utils.emptyString
import com.jero.core.viewmodel.BaseViewContract

class SelectDatabaseViewContract: BaseViewContract() {
    data class UiState(
        val canDoBiometricAuthentication: Boolean = false,
        val isLoading: Boolean = false,
        val databasePassword: String = emptyString(),
        val showDatabasePasswordDialog: Boolean = false,
    )

    sealed class UiIntent {
        data class AskPasswordForUri(val uri: Uri, val isCreation: Boolean) : UiIntent()
        data object CreateDatabase : UiIntent()
        data object DoBiometricAuthentication : UiIntent()
        data class SetPasswordInRAM(val password: String) : UiIntent()
        data object SelectDatabase : UiIntent()
        data object SetupBiometricAuthentication : UiIntent()
        data object GoToAccountsScreen : UiIntent()
    }

    sealed class UiAction {
        data object CreateDatabase : UiAction()
        data object SelectDatabase : UiAction()
        data class DoBiometricAuthentication(val showDatabasePasswordDialog: () -> Unit) : UiAction()
        data object GoToAccountsScreen : UiAction()
        data class RequestPasswordForCreation(val uri: Uri) : UiAction()
        data class RequestPasswordForOpening(val uri: Uri) : UiAction()
    }
}
