package com.jero.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.jero.core.designsystem.R
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.core.utils.emptyString
import com.jero.designsystem.components.CustomDialog
import com.jero.designsystem.components.KPassAppBar
import com.jero.domain.file_manager.FileManager
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

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.sendIntent(UiIntent.LoadAccounts)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = { KPassAppBar(
            stringResource(R.string.accounts_screen_title),
            showAdditionalOptions = true,
            additionalOptions = listOf(
                stringResource(R.string.select_another_database),
                stringResource(R.string.github),
                stringResource(R.string.linkedin),
            ),
        ) {
            when (it) {
                0 -> {
                    viewModel.sendIntent(UiIntent.ClearPreferences)
                }
                1 -> {
                    viewModel.sendIntent(UiIntent.OpenExplorer(it))
                }
                2 -> {
                    viewModel.sendIntent(UiIntent.OpenExplorer(it))
                }
            }
        } },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = { viewModel.sendIntent(UiIntent.OnAddSeeAccount()) }
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.ic_add
                    ),
                    contentDescription = "Add",
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                state.accounts.forEachIndexed { index, account ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    viewModel.sendIntent(UiIntent.OnAddSeeAccount(account.id))
                                },
                                onLongClick = {
                                    viewModel.sendIntent(UiIntent.OnDeleteAccount(account.id))
                                }
                            )
                            .padding(16.dp)
                    ) {
                        Text(text = account.title)
                    }
                    if (index != state.accounts.lastIndex) {
                        HorizontalDivider()
                    }
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
            }
        }
    }

    BackHandler {
        (context as? Activity)?.finish()
    }
    HandleActions(viewModel.actions) { action ->
        when (action) {
            is UiAction.OnAddSeeAccount -> {
                if (action.accountId.isBlank()) {
                    composeNavigator.navigate(KPassScreen.AddEditAccount(emptyString()))
                } else {
                    composeNavigator.navigate(KPassScreen.AccountDetail(action.accountId))
                }
            }

            is UiAction.LoadAccounts -> {
                val accounts = fileManager.readAccounts(context, action.uri.toUri())
                viewModel.sendIntent(UiIntent.SetAccounts(accounts))
            }

            is UiAction.DeleteAccount -> {
                val updatedAccounts = fileManager.deleteAccount(context, action.uri, action.accountId)
                viewModel.sendIntent(UiIntent.SetAccounts(updatedAccounts))
            }

            UiAction.GoDatabaseSelection -> composeNavigator.navigateUp()

            is UiAction.OpenExplorer -> {
                val intent = Intent(Intent.ACTION_VIEW, action.uri)
                context.startActivity(intent)
            }
        }
    }
}
