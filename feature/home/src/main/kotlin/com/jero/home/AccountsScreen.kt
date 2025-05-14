package com.jero.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.domain.file_manager.FileManager
import com.jero.core.designsystem.R
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.designsystem.components.CustomDialog
import com.jero.designsystem.components.KPassAppBar
import com.jero.home.AccountsViewContract.UiAction
import com.jero.home.AccountsViewContract.UiIntent
import com.jero.home.AccountsViewContract.UiState
import com.jero.navigation.KPassScreen
import com.jero.navigation.currentComposeNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SharedTransitionScope.AccountsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: AccountsViewModel = koinViewModel()
) {
    SetStatusBarIconsColor()
    val composeNavigator = currentComposeNavigator
    val state by viewModel.state.collectAsState(UiState())

    val context = LocalContext.current
    val fileManager: FileManager = koinInject()

    HandleActions(viewModel.actions) { action ->
        when (action) {
            is UiAction.OnAddEditAccount ->
                composeNavigator.navigate(KPassScreen.AddEditAccount(action.accountId))

            is UiAction.LoadAccounts -> {
                val accounts = fileManager.readAccounts(context, action.uri.toUri())
                viewModel.sendIntent(UiIntent.SetAccounts(accounts))
            }

            is UiAction.DeleteAccount -> {
                val updatedAccounts = fileManager.deleteAccount(context, action.uri, action.accountId)
                viewModel.sendIntent(UiIntent.SetAccounts(updatedAccounts))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(UiIntent.LoadAccounts)
    }

    Scaffold(
        topBar = { KPassAppBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_qr_code_24),
                contentDescription = null,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            state.accounts.forEach { account ->
                Text(
                    text = account.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .combinedClickable(
                            onClick = {
                                viewModel.sendIntent(UiIntent.OnAddEditAccount(account.id))
                            },
                            onLongClick = {
                                viewModel.sendIntent(UiIntent.OnDeleteAccount(account.id))
                            }
                        )
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.sendIntent(UiIntent.OnAddEditAccount())
                }
            ) {
                Text(text = "Add Account")
            }

            if (state.showDeleteAccountDialog) {
                CustomDialog("** Delete this account?", "** Esta acción no se puede deshacer",
                    onAccept = {
                        viewModel.sendIntent(UiIntent.DeleteAccount)
                    },
                    onCancel = {
                        viewModel.sendIntent(UiIntent.HideDeleteAccountDialog)
                    }
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.sendIntent(UiIntent.ClearPreferences)
                }
            ) {
                Text(text = "Clear Preferences")
            }
        }
    }
}
