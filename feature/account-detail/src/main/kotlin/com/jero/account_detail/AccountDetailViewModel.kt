package com.jero.account_detail

import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import com.example.domain.preferences.PreferencesHandler
import com.jero.core.viewmodel.BaseViewModelWithActions
import com.jero.account_detail.AccountDetailViewContract.UiState
import com.jero.account_detail.AccountDetailViewContract.UiIntent
import com.jero.account_detail.AccountDetailViewContract.UiAction

class AccountDetailViewModel(
    private val preferencesHandler: PreferencesHandler,
    savedStateHandle: SavedStateHandle,
) : BaseViewModelWithActions<UiState, UiIntent, UiAction>() {

    override val initialViewState = UiState()
    private val accountId: String = checkNotNull(savedStateHandle["id"])

    override suspend fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.DeleteAccount -> deleteAccount()
            UiIntent.HideDeleteAccountDialog -> setState {
                copy(showDeleteAccountDialog = false)
            }
            is UiIntent.EditAccount -> dispatchAction(UiAction.EditAccount(accountId = state.value.account.id))
            UiIntent.LoadAccountData -> loadAccountData()
            is UiIntent.ShowAccountData -> setState {
                copy(
                    account = intent.account,
                    isLoading = false
                )
            }

            UiIntent.OnDeleteAccount -> setState {
                copy(showDeleteAccountDialog = true)
            }
        }
    }

    init {
        loadAccountData()
    }

    private fun loadAccountData() {
        setState { copy(isLoading = true) }
        dispatchAction(
            UiAction.LoadAccountData(
                accountId = accountId,
                databaseUri = preferencesHandler.databaseUri.orEmpty().toUri()
            )
        )
    }

    private fun deleteAccount() {
        setState {
            copy(showDeleteAccountDialog = false)
        }
        dispatchAction(
            UiAction.OnDeleteAccount(
                preferencesHandler.databaseUri.orEmpty().toUri()
            )
        )
    }
}
