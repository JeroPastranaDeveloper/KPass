package com.jero.add_edit_account

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import com.example.domain.preferences.PreferencesHandler
import com.jero.add_edit_account.AddEditAccountViewContract.UiAction
import com.jero.add_edit_account.AddEditAccountViewContract.UiIntent
import com.jero.add_edit_account.AddEditAccountViewContract.UiState
import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewModelWithActions

class AddEditAccountViewModel(
    private val preferencesHandler: PreferencesHandler,
    savedStateHandle: SavedStateHandle,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    private val accountId: String = checkNotNull(savedStateHandle["id"])

    override val initialViewState = UiState()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.HideErrorDialog -> setState { copy(showErrorDialog = false) }
            is UiIntent.SaveAccount -> saveAccount(intent.account)
            is UiIntent.SetAccount -> setAccount(intent.account)
        }
    }

    init {
        loadAccount()
    }

    private fun loadAccount() {
        setState { copy(isLoading = true) }
        if (accountId.isBlank()) {
            setState { copy(isLoading = false) }
        } else {
            dispatchAction(UiAction.LoadAccountData(accountId, preferencesHandler.databaseUri.toString().toUri()))
        }
    }

    private fun saveAccount(account: Account) {
        val accountWithId = if (account.id.isBlank()) {
            account.copy(id = generateRandomId())
        } else {
            account
        }
        dispatchAction(
            UiAction.SaveOnDatabase(preferencesHandler.databaseUri.orEmpty(), accountWithId)
        )
    }

    private fun setAccount(account: Account?) {
        if (account != null) {
            setState { copy(account = account, isLoading = false) }
        } else {
            setState { copy(isLoading = false, showErrorDialog = true) }
        }
    }

    private fun generateRandomId(): String = java.util.UUID.randomUUID().toString()
}
