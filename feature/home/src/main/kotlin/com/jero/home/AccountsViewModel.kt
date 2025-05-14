package com.jero.home

import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.example.domain.preferences.PreferencesHandler
import com.jero.core.model.Account
import com.jero.core.utils.emptyPairStrings
import com.jero.core.viewmodel.BaseViewModelWithActions
import com.jero.home.AccountsViewContract.UiAction
import com.jero.home.AccountsViewContract.UiIntent
import com.jero.home.AccountsViewContract.UiState
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val preferencesHandler: PreferencesHandler,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()
    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.ClearPreferences -> preferencesHandler.clear()
            UiIntent.DeleteAccount -> deleteAccount()
            UiIntent.HideDeleteAccountDialog -> setState {
                copy(
                    showDeleteAccountDialog = false,
                    selectedAccount = emptyPairStrings
                )
            }

            is UiIntent.LoadAccounts -> loadAccounts()
            is UiIntent.OnAddEditAccount -> dispatchAction(
                UiAction.OnAddEditAccount(
                    accountId = intent.accountId
                )
            )

            is UiIntent.OnDeleteAccount -> showDeleteAccountDialog(intent.accountId)

            is UiIntent.SetAccounts -> setAccounts(intent.accounts)
        }
    }

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            dispatchAction(UiAction.LoadAccounts(preferencesHandler.databaseUri.orEmpty()))
        }
    }

    private fun setAccounts(accounts: List<Account>) {
        setState { copy(accounts = accounts, isLoading = false) }
    }

    private fun showDeleteAccountDialog(accountId: String) {
        val uri = preferencesHandler.databaseUri.orEmpty()
        setState {
            copy(
                showDeleteAccountDialog = true,
                selectedAccount = Pair<String, String>(accountId, uri)
            )
        }
    }

    private fun deleteAccount() {
        dispatchAction(
            UiAction.DeleteAccount(
                state.value.selectedAccount.first,
                state.value.selectedAccount.second.toUri()
            )
        )
        setState { copy(isLoading = false, selectedAccount = emptyPairStrings, showDeleteAccountDialog = false) }
    }
}
