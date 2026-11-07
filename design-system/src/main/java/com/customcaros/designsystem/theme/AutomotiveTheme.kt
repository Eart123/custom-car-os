package com.customcaros.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.customcaros.designsystem.R

object AutomotiveColors {
    val BackgroundDark = Color(0xFF0A0A0A)
    val BackgroundSurface = Color(0xFF141414)
    val BackgroundCard = Color(0xFF1E1E1E)
    val BackgroundElevated = Color(0xFF252525)

    val Primary = Color(0xFF00D4FF)
    val PrimaryContainer = Color(0xFF1A3B4A)
    val OnPrimary = Color(0xFF000000)

    val Secondary = Color(0xFF64FFDA)
    val OnSecondary = Color(0xFF000000)

    val Tertiary = Color(0xFFCF66FF)
    val OnTertiary = Color(0xFF000000)

    val Surface = Color(0xFF141414)
    val SurfaceVariant = Color(0xFF2A2A2A)
    val OnSurface = Color(0xFFE0E0E0)
    val OnSurfaceVariant = Color(0xFFB0B0B0)
    val Outline = Color(0xFF3A3A3A)

    val Success = Color(0xFF00E676)
    val Warning = Color(0xFFFFF176)
    val Error = Color(0xFFFF5252)
    val Info = Color(0xFF29B6F6)

    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB0B0B0)
    val TextDisabled = Color(0xFF4A4A4A)

    val DashboardGlow = Color(0xFF00D4FF)
    val SpeedometerNeedle = Color(0xFFFF5252)
}

object AutomotiveTypography {
    val Huge = 48.sp
    val Large = 32.sp
    val Medium = 20.sp
    val Body = 16.sp
    val Small = 14.sp
    val Caption = 12.sp
    val Tiny = 10.sp
}

object AutomotiveSpacing {
    val Xs = 4.dp
    val Sm = 8.dp
    val Md = 12.dp
    val Lg = 16.dp
    val Xl = 20.dp
    val Xxl = 24.dp
    val Xxxl = 32.dp
    val Giant = 48.dp
}

object AutomotiveShape {
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val XLarge = 20.dp
    val Full = 50.dp
}

object AutomotiveAnimation {
    val Fast = 150L
    val Normal = 300L
    val Slow = 500L
}

val DarkAutomotiveColorScheme = darkColorScheme(
    primary = AutomotiveColors.Primary,
    onPrimary = AutomotiveColors.OnPrimary,
    primaryContainer = AutomotiveColors.PrimaryContainer,
    secondary = AutomotiveColors.Secondary,
    onSecondary = AutomotiveColors.OnSecondary,
    tertiary = AutomotiveColors.Tertiary,
    onTertiary = AutomotiveColors.OnTertiary,
    surface = AutomotiveColors.Surface,
    surfaceVariant = AutomotiveColors.SurfaceVariant,
    onSurface = AutomotiveColors.OnSurface,
    onSurfaceVariant = AutomotiveColors.OnSurfaceVariant,
    outline = AutomotiveColors.Outline,
    background = AutomotiveColors.BackgroundDark,
    onBackground = AutomotiveColors.TextPrimary,
    surfaceContainerLowest = AutomotiveColors.BackgroundDark,
    surfaceContainerLow = AutomotiveColors.BackgroundSurface,
    surfaceContainer = AutomotiveColors.BackgroundCard,
    surfaceContainerHigh = AutomotiveColors.BackgroundElevated,
    surfaceContainerHighest = AutomotiveColors.BackgroundElevated,
    inverseSurface = AutomotiveColors.BackgroundCard,
    inverseOnSurface = AutomotiveColors.TextPrimary,
    inversePrimary = AutomotiveColors.Primary,
    error = AutomotiveColors.Error,
    errorContainer = Color(0xFF1A0000),
    onError = AutomotiveColors.TextPrimary,
    onErrorContainer = AutomotiveColors.TextPrimary
)

val LightAutomotiveColorScheme = lightColorScheme(
    primary = AutomotiveColors.Primary,
    onPrimary = AutomotiveColors.OnPrimary,
    primaryContainer = AutomotiveColors.PrimaryContainer,
    secondary = AutomotiveColors.Secondary,
    onSecondary = AutomotiveColors.OnSecondary,
    tertiary = AutomotiveColors.Tertiary,
    onTertiary = AutomotiveColors.OnTertiary,
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1A1A1A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1A1A),
    outline = AutomotiveColors.Outline
)

@Composable
fun AutomotiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkAutomotiveColorScheme else LightAutomotiveColorScheme
    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
