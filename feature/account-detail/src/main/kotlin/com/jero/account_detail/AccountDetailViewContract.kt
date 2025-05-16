package com.jero.account_detail

import android.net.Uri
import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewContract

class AccountDetailViewContract : BaseViewContract() {
    data class UiState(
        val account: Account = Account(),
        val isLoading: Boolean = false,
        val showDeleteAccountDialog: Boolean = false,
    )

    sealed class UiIntent {
        data object DeleteAccount : UiIntent()
        data object EditAccount : UiIntent()
        data object HideDeleteAccountDialog : UiIntent()
        data object LoadAccountData : UiIntent()
        data object OnDeleteAccount : UiIntent()
        data class ShowAccountData(val account: Account) : UiIntent()
    }

    sealed class UiAction {
        data class EditAccount(val accountId: String) : UiAction()
        data class LoadAccountData(val accountId: String, val databaseUri: Uri) : UiAction()
        data class OnDeleteAccount(val databaseUri: Uri) : UiAction()
    }
}
