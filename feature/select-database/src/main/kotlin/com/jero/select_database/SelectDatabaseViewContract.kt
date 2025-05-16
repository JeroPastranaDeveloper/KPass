package com.jero.select_database

import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewContract

class SelectDatabaseViewContract: BaseViewContract() {
    data class UiState(
        val accounts: List<Account> = emptyList(),
        val isLoading: Boolean = false,
        val canDoBiometricAuthentication: Boolean = false,
    )

    sealed class UiIntent {
        data object CreateDatabase : UiIntent()
        data object DoBiometricAuthentication : UiIntent()
        data object SelectDatabase : UiIntent()
        data object SetupBiometricAuthentication : UiIntent()
        data class SyncWithFileUri(val uri: String) : UiIntent()
    }

    sealed class UiAction {
        data object CreateDatabase : UiAction()
        data class DoBiometricAuthentication(val goToAccountsScreen: () -> Unit) : UiAction()
        data object SelectDatabase : UiAction()
        data object GoToAccountsScreen : UiAction()
    }
}
