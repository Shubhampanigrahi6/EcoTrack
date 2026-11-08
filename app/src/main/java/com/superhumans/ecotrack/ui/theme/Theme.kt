
package com.superhumans.ecotrack.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EcoTrackColorScheme = lightColorScheme(

    primary = EcoBlue,
    onPrimary = EcoWhite,

    primaryContainer = EcoSurfaceBlue,
    onPrimaryContainer = EcoNavy,

    secondary = EcoNavy,
    onSecondary = EcoWhite,

    secondaryContainer = EcoSurfaceBlue,
    onSecondaryContainer = EcoNavy,

    tertiary = EcoCoral,
    onTertiary = EcoWhite,

    background = EcoBackground,
    onBackground = EcoNavy,

    surface = EcoSurface,
    onSurface = EcoNavy,

    surfaceVariant = EcoSurfaceBlue,
    onSurfaceVariant = EcoTextSecondary,

    outline = EcoBorder,
    outlineVariant = EcoDivider,

    error = EcoError,
    onError = EcoWhite
)

@Composable
fun EcoTrackTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EcoTrackColorScheme,
        typography = Typography,
        content = content
    )
}
