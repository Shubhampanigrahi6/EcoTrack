
package com.superhumans.ecotrack.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.ui.theme.EcoGradientEnd
import com.superhumans.ecotrack.ui.theme.EcoGradientStart
import com.superhumans.ecotrack.ui.theme.EcoWhite

@Composable
fun EcoTrackButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)

    val gradient = Brush.horizontalGradient(
        colors = listOf(EcoGradientStart, EcoGradientEnd)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(shape)
            .background(
                brush = if (enabled) gradient
                else Brush.horizontalGradient(
                    listOf(Color.LightGray, Color.Gray)
                )
            )
            .clickable(
                enabled = enabled && !loading,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = EcoWhite,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                color = EcoWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
