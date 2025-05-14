package com.jero.add_edit_account

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.roomdatabase.GetAccountById
import com.example.domain.repository.roomdatabase.SaveAccountRepository
import com.jero.add_edit_account.AddEditAccountViewContract.UiIntent
import com.jero.add_edit_account.AddEditAccountViewContract.UiState
import com.jero.add_edit_account.AddEditAccountViewContract.UiAction
import com.jero.core.model.Account
import com.jero.core.viewmodel.BaseViewModelWithActions
import kotlinx.coroutines.launch

class AddEditAccountViewModel(
    private val getAccountById: GetAccountById,
    private val saveAccountRepository: SaveAccountRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    private val accountId: String = checkNotNull(savedStateHandle["id"])

    override val initialViewState = UiState()

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.SaveAccount -> saveAccount(intent.account)
        }
    }

    init {
        loadAccount()
    }

    private fun loadAccount() {
        viewModelScope.launch {
            if (accountId.isBlank()) return@launch

            setState { copy(isLoading = true) }

            val account = getAccountById(accountId)

            setState {
                copy(account = account, isLoading = false)
            }
        }
    }

    private fun saveAccount(account: Account) {
        viewModelScope.launch {
            saveAccountRepository(account)
        }
    }
}
