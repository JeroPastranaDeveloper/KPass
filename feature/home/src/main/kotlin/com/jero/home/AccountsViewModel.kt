package com.jero.home

import androidx.lifecycle.viewModelScope
import com.example.domain.preferences.PreferencesHandler
import com.example.domain.repository.roomdatabase.GetAccountsRepository
import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewModelWithActions
import com.jero.home.AccountsViewContract.UiIntent
import com.jero.home.AccountsViewContract.UiState
import com.jero.home.AccountsViewContract.UiAction
import kotlinx.coroutines.launch

class AccountsViewModel(
    private val getAccountsRepository: GetAccountsRepository,
    private val preferencesHandler: PreferencesHandler,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()
    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.LoadAccounts -> loadAccounts()
            is UiIntent.OnAddEditAccount -> dispatchAction(
                UiAction.OnAddEditAccount(
                    accountId = intent.accountId
                )
            )

            UiIntent.ClearPreferences -> preferencesHandler.clear()
        }
    }

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val accounts = getAccountsRepository()
            if (accounts.isEmpty()) {
                dispatchAction(UiAction.CreateDatabase)
            }

            setState {
                copy(
                    accounts = accounts,
                    isLoading = false
                )
            }
        }
    }

    private fun syncWithFile(fileAccounts: List<Account>) {
        viewModelScope.launch {
            val roomAccounts = getAccountsRepository()
            val accountsToAdd = roomAccounts.filterNot { roomAcc ->
                fileAccounts.any { fileAcc -> fileAcc.id == roomAcc.id }
            }

            if (accountsToAdd.isNotEmpty()) {
                // Aquí vuelcas las cuentas a archivo
                // Debes tener la Uri o solicitar de nuevo crear documento
                dispatchAction(UiAction.RequestExport(accountsToAdd))
            }
        }
    }
}
