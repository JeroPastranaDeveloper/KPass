package com.jero.select_database

import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewContract

class SelectDatabaseViewContract: BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val accounts: List<Account> = emptyList()
    )

    sealed class UiIntent {
        data object CreateDatabase : UiIntent()
        data object GoToAccountsScreen : UiIntent()
        data object SelectDatabase : UiIntent()
        data class SyncWithFileUri(val uri: String) : UiIntent()
    }

    sealed class UiAction {
        data object CreateDatabase : UiAction()
        data object SelectDatabase : UiAction()
        data object GoToAccountsScreen : UiAction()
    }
}
