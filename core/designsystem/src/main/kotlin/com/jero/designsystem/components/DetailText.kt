package com.jero.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.jero.core.model.DetailType
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jero.core.designsystem.R

@Composable
fun DetailText(text: String, type: DetailType) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = when (type) {
                DetailType.EMAIL -> painterResource(id = R.drawable.ic_email)
                DetailType.PASSWORD -> painterResource(id = R.drawable.ic_password)
                DetailType.DESCRIPTION -> painterResource(id = R.drawable.ic_description)
            },
            contentDescription = null,
            tint = Color.Gray
        )

        Column {
            Text(
                text = when (type) {
                    DetailType.EMAIL -> stringResource(R.string.email)
                    DetailType.PASSWORD -> stringResource(R.string.password)
                    DetailType.DESCRIPTION -> stringResource(R.string.description)
                },
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray,
                fontSize = 12.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp,
            )
        }
    }
}