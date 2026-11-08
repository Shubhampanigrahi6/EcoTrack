
package com.superhumans.ecotrack.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.superhumans.ecotrack.ui.theme.EcoBorder
import com.superhumans.ecotrack.ui.theme.EcoSurface

@Composable
fun EcoTrackCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = EcoSurface,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = backgroundColor,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(
            0.7.dp,
            EcoBorder
        )
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}
