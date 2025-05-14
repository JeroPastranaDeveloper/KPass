package com.jero.add_edit_account

import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewContract

class AddEditAccountViewContract: BaseViewContract() {
    data class UiState(
        val isLoading: Boolean = false,
        val account: Account = Account()
    )

    sealed class UiIntent {
        data class SaveAccount(val account: Account) : UiIntent()
    }

    sealed class UiAction {
        data class SaveOnDatabase(val info: String) : UiAction()
    }
}
