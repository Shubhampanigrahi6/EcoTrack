
package com.superhumans.ecotrack.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.superhumans.ecotrack.ui.theme.EcoBlue
import com.superhumans.ecotrack.ui.theme.EcoSurfaceBlue

@Composable
fun EcoTrackProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = EcoSurfaceBlue,
    progressColor: Color = EcoBlue
) {
    val safeProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(9.dp)
            .clip(RoundedCornerShape(50))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(safeProgress)
                .height(9.dp)
                .clip(RoundedCornerShape(50))
                .background(progressColor)
        )
    }
}
