package com.jero.select_database

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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.jero.core.designsystem.R
import com.jero.core.model.Account
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.designsystem.components.KPassAppBar
import com.jero.navigation.KPassScreen
import com.jero.navigation.currentComposeNavigator
import com.jero.select_database.SelectDatabaseViewContract.UiAction
import com.jero.select_database.SelectDatabaseViewContract.UiIntent
import com.jero.select_database.SelectDatabaseViewContract.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SharedTransitionScope.SelectDatabaseScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: SelectDatabaseViewModel = koinViewModel()
) {
    val composeNavigator = currentComposeNavigator
    val state by viewModel.state.collectAsState(UiState())

    SetStatusBarIconsColor()

    val context = LocalContext.current

    // Exportar base de datos
    val createDatabaseLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.openOutputStream(it)?.use { outputStream ->
                val emptyJson = "[]"
                outputStream.write(emptyJson.toByteArray())
            }

            // Guardamos la URI en preferencias
            viewModel.sendIntent(UiIntent.SyncWithFileUri(it.toString()))
        }
    }

    // Abrir base de datos existente
    val openDatabaseLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                // Guardar la URI en SharedPreferences
                viewModel.sendIntent(UiIntent.SyncWithFileUri(it.toString()))
            }
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

private fun parseAccountsFromFile(fileData: ByteArray): List<Account> {
    val json = String(fileData)
    val gson = Gson()
    val type = object : TypeToken<List<Account>>() {}.type
    return gson.fromJson(json, type)
}

private fun toJson(accounts: List<Account>): String {
    return Gson().toJson(accounts)
}
