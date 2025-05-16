package com.jero.account_detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.file_manager.FileManager
import com.jero.account_detail.AccountDetailViewContract.UiAction
import com.jero.account_detail.AccountDetailViewContract.UiIntent
import com.jero.account_detail.AccountDetailViewContract.UiState
import com.jero.core.designsystem.R
import com.jero.core.model.Account
import com.jero.core.model.DetailType
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.designsystem.components.CustomDialog
import com.jero.designsystem.components.DetailText
import com.jero.designsystem.components.KPassAppBar
import com.jero.navigation.KPassScreen.AddEditAccount
import com.jero.navigation.currentComposeNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SharedTransitionScope.AccountDetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: AccountDetailViewModel = koinViewModel()
) {
    SetStatusBarIconsColor()
    val composeNavigator = currentComposeNavigator
    val state by viewModel.state.collectAsState(UiState())

    val context = LocalContext.current
    val fileManager: FileManager = koinInject()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(UiIntent.LoadAccountData)
    }

    HandleActions(viewModel.actions) { action ->
        when (action) {
            is UiAction.EditAccount -> composeNavigator.navigate(AddEditAccount(action.accountId))
            is UiAction.LoadAccountData -> {
                val account =
                    fileManager.readAccountById(context, action.databaseUri, action.accountId)
                viewModel.sendIntent(UiIntent.ShowAccountData(account ?: Account()))
            }

            is UiAction.OnDeleteAccount -> {
                fileManager.deleteAccount(context, action.databaseUri, state.account.id)
                composeNavigator.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            KPassAppBar(
                state.account.title,
                showAdditionalOptions = true,
                additionalOptions = listOf(
                    stringResource(R.string.action_edit),
                    stringResource(R.string.action_delete),
                )
            ) { index ->
                when (index) {
                    0 -> {
                        viewModel.sendIntent(UiIntent.EditAccount)
                    }
                    1 -> {
                        viewModel.sendIntent(UiIntent.OnDeleteAccount)
                    }
                }
            }
        },
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {
                    viewModel.sendIntent(
                        UiIntent.EditAccount
                    )
                }
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.ic_edit
                    ),
                    contentDescription = "Edit"
                )
            }

            if (state.showDeleteAccountDialog) {
                CustomDialog(
                    stringResource(R.string.delete_account), stringResource(R.string.can_not_undone_action),
                    onAccept = {
                        viewModel.sendIntent(UiIntent.DeleteAccount)
                    },
                    onCancel = {
                        viewModel.sendIntent(UiIntent.HideDeleteAccountDialog)
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                DetailText(
                    text = state.account.email,
                    DetailType.EMAIL,
                )
                Spacer(modifier = Modifier.height(16.dp))
                DetailText(
                    text = state.account.password,
                    DetailType.PASSWORD,
                )
                Spacer(modifier = Modifier.height(16.dp))
                DetailText(
                    text = state.account.description,
                    DetailType.DESCRIPTION,
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
