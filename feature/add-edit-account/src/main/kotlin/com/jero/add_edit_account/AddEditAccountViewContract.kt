package com.jero.add_edit_account

import android.net.Uri
import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewContract
import com.jero.core.utils.emptyString

class AddEditAccountViewContract: BaseViewContract() {
    data class UiState(
        val accountIdentifier: String = emptyString(),
        val isLoading: Boolean = false,
        val showErrorDialog: Boolean = false,
        val account: Account = Account()
    )

    sealed class UiIntent {
        data object HideErrorDialog : UiIntent()
        data class SaveAccount(val account: Account) : UiIntent()
        data class SetAccount(val account: Account?) : UiIntent()
    }

    sealed class UiAction {
        data class LoadAccountData(val accountId: String, val databaseUri: Uri) : UiAction()
        data class SaveOnDatabase(val uri: String, val info: Account) : UiAction()
    }
}
