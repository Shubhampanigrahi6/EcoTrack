
package com.superhumans.ecotrack.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.ui.theme.EcoBlue
import com.superhumans.ecotrack.ui.theme.EcoNavy
import com.superhumans.ecotrack.ui.theme.EcoTextSecondary

@Composable
fun EcoTrackTopBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 13.sp,
                    color = EcoTextSecondary
                )
            }
        }

        actionIcon?.let {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = it,
                    contentDescription = "Header action",
                    tint = EcoBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
