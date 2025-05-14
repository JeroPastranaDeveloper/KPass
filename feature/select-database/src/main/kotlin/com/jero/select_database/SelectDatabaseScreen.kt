package com.jero.select_database

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.file_permissions_manager.FilePermissionManager
import com.jero.core.designsystem.R
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.designsystem.components.KPassAppBar
import com.jero.navigation.KPassScreen
import com.jero.navigation.currentComposeNavigator
import com.jero.select_database.SelectDatabaseViewContract.UiAction
import com.jero.select_database.SelectDatabaseViewContract.UiIntent
import com.jero.select_database.SelectDatabaseViewContract.UiState
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SharedTransitionScope.SelectDatabaseScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: SelectDatabaseViewModel = koinViewModel()
) {
    val composeNavigator = currentComposeNavigator
    val state by viewModel.state.collectAsState(UiState())

    SetStatusBarIconsColor()

    val context = LocalContext.current

    val filePermissionManager: FilePermissionManager = koinInject()

    val createDatabaseLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri: Uri? ->
        uri?.let {
            filePermissionManager.checkPermissions(context, it)
            filePermissionManager.initializeDatabaseFile(context, it)
            viewModel.sendIntent(UiIntent.SyncWithFileUri(it.toString()))
        }
    }

    val openDatabaseLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            filePermissionManager.checkPermissions(context, it)
            viewModel.sendIntent(UiIntent.SyncWithFileUri(it.toString()))
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

            Button(
                onClick = {
                    viewModel.sendIntent(UiIntent.CreateDatabase)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Create Database",
                )
            }

            Button(
                onClick = {
                    viewModel.sendIntent(UiIntent.SelectDatabase)
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Select Database",
                )
            }
        }
    }

    HandleActions(viewModel.actions) { action ->
        when (action) {
            is UiAction.SelectDatabase -> openDatabaseLauncher.launch(arrayOf("application/octet-stream"))

            UiAction.GoToAccountsScreen -> composeNavigator.navigate(KPassScreen.Accounts)

            UiAction.CreateDatabase -> createDatabaseLauncher.launch("kpass_backup.kdbx")
        }
    }
}

private fun checkPermissions(context: Context, uri1: Uri) {
    val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    context.contentResolver.takePersistableUriPermission(uri1, flags)
}
