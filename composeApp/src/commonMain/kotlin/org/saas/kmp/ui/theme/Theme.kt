package org.saas.kmp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CustomBlue,
    onPrimary = White,
    primaryContainer = CustomBlueLight,
    onPrimaryContainer = Black,
    secondary = CustomBlueDark,
    onSecondary = White,
    tertiary = CustomBlue,
    onTertiary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = LightGray,
    onSurfaceVariant = Gray
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
