package com.jero.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.jero.core.designsystem.R
import com.jero.core.model.Account
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.designsystem.components.KPassAppBar
import com.jero.home.AccountsViewContract.UiAction
import com.jero.home.AccountsViewContract.UiIntent
import com.jero.home.AccountsViewContract.UiState
import com.jero.navigation.KPassScreen
import com.jero.navigation.currentComposeNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun SharedTransitionScope.AccountsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: AccountsViewModel = koinViewModel()
) {
    SetStatusBarIconsColor()
    val composeNavigator = currentComposeNavigator
    val state by viewModel.state.collectAsState(UiState())

    val context = LocalContext.current

    HandleActions(viewModel.actions) { action ->
        when (action) {
            is UiAction.CreateDatabase,
            is UiAction.RequestExport -> {
                // createDatabaseLauncher.launch("kpass_backup.kdbx")
            }

            is UiAction.OnAddEditAccount ->
                composeNavigator.navigate(KPassScreen.AddEditAccount(action.accountId))
        }
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
                    text = account.email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            viewModel.sendIntent(UiIntent.OnAddEditAccount(account.id))
                        }
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
