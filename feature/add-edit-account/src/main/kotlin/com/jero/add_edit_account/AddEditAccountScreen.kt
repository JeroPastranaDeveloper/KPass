package com.jero.add_edit_account

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jero.add_edit_account.AddEditAccountViewContract.UiState
import com.jero.add_edit_account.AddEditAccountViewContract.UiIntent
import com.jero.core.model.Account
import com.jero.core.screen.HandleActions
import com.jero.core.screen.SetStatusBarIconsColor
import com.jero.designsystem.components.KPassAppBar
import com.jero.navigation.currentComposeNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun SharedTransitionScope.AddEditAccountScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: AddEditAccountViewModel = koinViewModel()
) {
    SetStatusBarIconsColor()
    val composeNavigator = currentComposeNavigator
    val state by viewModel.state.collectAsState(UiState())
    Scaffold(
        topBar = { KPassAppBar() },
    ) { paddingValues ->
        val email = remember { mutableStateOf(TextFieldValue("")) }
        val password = remember { mutableStateOf(TextFieldValue("")) }
        val description = remember { mutableStateOf(TextFieldValue("")) }

        LaunchedEffect(state.account) {
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
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                label = { Text("Username") },
                modifier = Modifier.padding(vertical = 16.dp)
            )

            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = { Text("Password") },
                modifier = Modifier.padding(16.dp)
            )

            TextField(
                value = description.value,
                onValueChange = {
                    description.value = it
                },
                label = { Text("Description") },
                modifier = Modifier.padding(16.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = {
                    val account = Account(
                        id = state.account.id,
                        title = state.account.title,
                        email = email.value.text,
                        password = password.value.text,
                        description = description.value.text,
                    )
                    viewModel.sendIntent(UiIntent.SaveAccount(account))
                    composeNavigator.navigateUp()
                }
            ) {
                Text(text = "Save")
            }
        }
    }

    HandleActions(viewModel.actions) { action ->
        when (action) {
            is AddEditAccountViewContract.UiAction.SaveOnDatabase -> TODO()
        }
    }
}
