package com.jero.home

import android.net.Uri
import androidx.core.net.toUri
import com.example.domain.preferences.PreferencesHandler
import com.jero.core.model.Account
import com.jero.core.utils.emptyPairStrings
import com.jero.core.viewmodel.BaseViewModelWithActions
import com.jero.home.AccountsViewContract.UiAction
import com.jero.home.AccountsViewContract.UiIntent
import com.jero.home.AccountsViewContract.UiState

class AccountsViewModel(
    private val preferencesHandler: PreferencesHandler,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()
    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.ClearPreferences -> clearPreferences()
            UiIntent.DeleteAccount -> deleteAccount()
            UiIntent.HideDeleteAccountDialog -> setState {
                copy(
                    showDeleteAccountDialog = false,
                    selectedAccount = emptyPairStrings
                )
            }

            is UiIntent.LoadAccounts -> loadAccounts()
            is UiIntent.OnAddSeeAccount -> dispatchAction(
                UiAction.OnAddSeeAccount(
                    accountId = intent.accountId
                )
            )

            is UiIntent.OnDeleteAccount -> showDeleteAccountDialog(intent.accountId)
            is UiIntent.OpenExplorer -> dispatchAction(
                UiAction.OpenExplorer(uri = getUri(intent.index))
            )

            is UiIntent.SetAccounts -> setAccounts(intent.accounts)
        }
    }

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        setState { copy(isLoading = true) }
        preferencesHandler.isLogged = true
        dispatchAction(UiAction.LoadAccounts(preferencesHandler.databaseUri.orEmpty()))
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
        setState {
            copy(
                isLoading = false,
                selectedAccount = emptyPairStrings,
                showDeleteAccountDialog = false
            )
        }
    }

    private fun clearPreferences() {
        preferencesHandler.clear()
        setState { copy(isLoading = false, accounts = emptyList()) }
        dispatchAction(UiAction.GoDatabaseSelection)
    }

    private fun getUri(index: Int): Uri = when (index) {
        1 -> "https://github.com/JeroPastranaDeveloper"
        else -> "https://www.linkedin.com/in/jero-pastrana/"
    }.toUri()
}
