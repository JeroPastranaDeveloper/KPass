package com.jero.home

import android.net.Uri
import com.jero.core.model.Account
import com.jero.core.utils.emptyPairStrings
import com.jero.core.utils.emptyString
import com.jero.core.viewmodel.BaseViewContract

class AccountsViewContract: BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val accounts: List<Account> = emptyList(),
        val selectedAccount: Pair<String, String> = emptyPairStrings,
        val showDeleteAccountDialog: Boolean = false,
    )

    sealed class UiIntent {
        data object ClearPreferences : UiIntent()
        data object DeleteAccount : UiIntent()
        data object HideDeleteAccountDialog : UiIntent()
        data object LoadAccounts : UiIntent()
        data class OnAddSeeAccount(val accountId: String = emptyString()) : UiIntent()
        data class OnDeleteAccount(val accountId: String) : UiIntent()
        data class OpenExplorer(val index: Int) : UiIntent()
        data class SetAccounts(val accounts: List<Account>) : UiIntent()
    }

    sealed class UiAction {
        data class DeleteAccount(val accountId: String, val uri: Uri) : UiAction()
        data object GoDatabaseSelection : UiAction()
        data class LoadAccounts(val uri: String) : UiAction()
        data class OnAddSeeAccount(val accountId: String) : UiAction()
        data class OpenExplorer(val uri: Uri) : UiAction()
    }
}
