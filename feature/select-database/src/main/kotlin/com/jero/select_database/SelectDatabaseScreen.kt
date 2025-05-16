package com.jero.select_database

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.domain.file_permissions_manager.FilePermissionManager
import com.jero.biometric_authentication.BiometricAuthenticator
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

    LaunchedEffect(Unit) {
        viewModel.sendIntent(UiIntent.SetupBiometricAuthentication)
    }

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
            Spacer(modifier = Modifier.weight(1f))
            if (state.canDoBiometricAuthentication) {
                Image(
                    painter = painterResource(id = R.drawable.ic_fingerprint),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .size(64.dp)
                        .clickable {
                            viewModel.sendIntent(UiIntent.DoBiometricAuthentication)
                        }
                )
            }

            Button(
                onClick = {
                    viewModel.sendIntent(UiIntent.CreateDatabase)
                },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Create Database",
                )
            }

            Button(
                onClick = {
                    viewModel.sendIntent(UiIntent.SelectDatabase)
                },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = "Select Database",
                )
            }
        }
    }

    HandleActions(viewModel.actions) { action ->
        when (action) {
            UiAction.CreateDatabase -> createDatabaseLauncher.launch("kpass_backup.kdbx")
            is UiAction.DoBiometricAuthentication -> {
                val activity = context as? FragmentActivity
                activity?.let {
                    BiometricAuthenticator(it).authenticate {
                        action.goToAccountsScreen()
                    }
                }
            }
            UiAction.GoToAccountsScreen -> composeNavigator.navigate(KPassScreen.Accounts)
            is UiAction.SelectDatabase -> openDatabaseLauncher.launch(arrayOf("application/octet-stream"))
        }
    }
}
