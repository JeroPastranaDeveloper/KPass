package com.jero.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jero.core.designsystem.R

@Composable
fun KPassAppBar(
    title: String = stringResource(id = R.string.app_name),
    showAdditionalOptions: Boolean = false,
    additionalOptions: List<String> = emptyList(),
    onMenuOptionClick: (Int) -> Unit = {}
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Color.Black,
        ),
        actions = {
            if (showAdditionalOptions) {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu options",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    additionalOptions.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                menuExpanded = false
                                onMenuOptionClick(index)
                            }
                        )
                    }
                }
            }
        }
    )
}
