package com.jero.add_edit_account

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.jero.add_edit_account.AddEditAccountViewContract.UiIntent
import com.jero.add_edit_account.AddEditAccountViewContract.UiState
import com.jero.core.designsystem.R
import com.jero.core.model.Account
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.core.utils.emptyString
import com.jero.designsystem.components.CustomDialog
import com.jero.designsystem.components.KPassAppBar
import com.jero.domain.file_manager.FileManager
import com.jero.navigation.currentComposeNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SharedTransitionScope.AddEditAccountScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: AddEditAccountViewModel = koinViewModel()
) {
    SetStatusBarIconsColor()
    val composeNavigator = currentComposeNavigator
    val fileManager: FileManager = koinInject()

    val state by viewModel.state.collectAsState(UiState())
    Scaffold(
        topBar = { KPassAppBar() },
    ) { paddingValues ->
        val title = remember { mutableStateOf(TextFieldValue(emptyString())) }
        val email = remember { mutableStateOf(TextFieldValue(emptyString())) }
        val password = remember { mutableStateOf(TextFieldValue(emptyString())) }
        val description = remember { mutableStateOf(TextFieldValue(emptyString())) }

        LaunchedEffect(state.account) {
            title.value = TextFieldValue(state.account.title)
            email.value = TextFieldValue(state.account.email)
            password.value = TextFieldValue(state.account.password)
            description.value = TextFieldValue(state.account.description)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title.value,
                onValueChange = {
                    title.value = it
                },
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            TextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = { Text(stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            TextField(
                value = description.value,
                onValueChange = {
                    description.value = it
                },
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    val account = Account(
                        id = state.account.id,
                        title = title.value.text,
                        email = email.value.text,
                        password = password.value.text,
                        description = description.value.text,
                    )
                    viewModel.sendIntent(UiIntent.SaveAccount(account))
                }
            ) {
                Text(text = "Save")
            }

            if (state.showErrorDialog) {
                CustomDialog(
                    titleText = "Error",
                    bodyText = "Error getting account data",
                    onAccept = {
                        viewModel.sendIntent(UiIntent.HideErrorDialog)
                    },
                    onCancel = {
                        viewModel.sendIntent(UiIntent.HideErrorDialog)
                    }
                )
            }
        }
    }
    val context = LocalContext.current
    HandleActions(viewModel.actions) { action ->
        when (action) {
            is AddEditAccountViewContract.UiAction.SaveOnDatabase -> {
                fileManager.upsertAccount(
                    context = context,
                    uri = action.uri.toUri(),
                    account = action.info
                )
                composeNavigator.navigateUp()
            }

            is AddEditAccountViewContract.UiAction.LoadAccountData -> {
                viewModel.sendIntent(
                    UiIntent.SetAccount(
                        fileManager.readAccountById(context, action.databaseUri, action.accountId)
                    )
                )
            }
        }
    }
}
