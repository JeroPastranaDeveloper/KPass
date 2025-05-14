package com.jero.home

import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewContract

class AccountsViewContract: BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val accounts: List<Account> = emptyList()
    )

    sealed class UiIntent {
        data object LoadAccounts : UiIntent()
        data class OnAddEditAccount(val accountId: String = "") : UiIntent()
        data object ClearPreferences : UiIntent()
    }

    sealed class UiAction {
        data object CreateDatabase : UiAction()
        data class OnAddEditAccount(val accountId: String) : UiAction()
        data class RequestExport(val accounts: List<Account>) : UiAction()
    }
}
