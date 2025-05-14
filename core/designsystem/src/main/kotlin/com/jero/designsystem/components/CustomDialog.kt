package com.jero.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CustomDialog(
    titleText: String,
    bodyText: String,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        title = { Text(text = titleText)},
        text = { Text(text = bodyText)},
        onDismissRequest = { onCancel() },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccept()
                }
            ) {
                Text("Accept")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
